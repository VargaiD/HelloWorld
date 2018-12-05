/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254.algorithms;

import com.google.common.collect.Lists;
import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.RecommendationDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.GenreFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Šimon Baláž
 */
@Component
@Transactional
public class CollaborativeFiltering {
    
    @Autowired
    private GameFacade gameFacade;
    @Autowired
    private RecommendationFacade recommendationFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private static GenreFacade genreFacade;
    
    private static double calculateMean(Set<RecommendationDTO> recommendations) {
        double sum = 0;
        if(!recommendations.isEmpty()) {
            for(RecommendationDTO rec : recommendations) {                
                if(rec.isVotedUp()) {
                    sum += 1;
                }
            }
        }
        else {
            return 0;
        }
        
        return sum / (double)recommendations.size();
    }
    
    private Map pearsonCoeficientMatrix(UserDTO user, List<GameDTO> recommendedGames,
            Set<RecommendationDTO> recommendations, List<UserDTO> sortedUsers) {
        
        Map<UserDTO, Double> coeficients = new HashMap(); 
        
        double userMean = calculateMean(recommendations);        
        
        for(UserDTO similarUser : sortedUsers) {
            double similarUserMean = calculateMean(new HashSet<>(recommendationFacade.findByAuthor(similarUser)));
            double numerator = 0;
            double denominator_1 = 0;  
            double denominator_2 = 0; 
            
            for(GameDTO game : recommendedGames) {                
                RecommendationDTO userRecommendation = recommendationFacade.findByAuthorAndGame(user, game);      
                RecommendationDTO similarUserRecommendation = recommendationFacade.findByAuthorAndGame(similarUser, game);
                if(similarUserRecommendation == null) {
                    continue;
                }                

                int userRecValue = 0;
                if(userRecommendation.isVotedUp()) {
                    userRecValue = 1;
                }
                
                int similarUserRecValue = 0;
                if(similarUserRecommendation.isVotedUp()) {
                    similarUserRecValue = 1;
                }
                
                numerator += (userRecValue - userMean)*(similarUserRecValue - similarUserMean);
                denominator_1 += Math.pow((userRecValue - userMean), 2);
                denominator_2 += Math.pow((similarUserRecValue - similarUserMean), 2);
            }             
            double coeficient = numerator / (Math.sqrt(denominator_1) * Math.sqrt(denominator_2));
            
            if(coeficient > 0 || coeficient < 0) {
                coeficients.put(similarUser, coeficient);
            }
        }  
        
        return coeficients;
    }
    
    private Map diceCoeficient(UserDTO user, List<GameDTO> recommendedGames,
            Set<RecommendationDTO> recommendations, List<UserDTO> sortedUsers) {
        
        Map<UserDTO, Double> coeficients = new HashMap(); 
        
        for(UserDTO similarUser: sortedUsers) {            
            List<GameDTO> gamesBySimilarUser = gameFacade.findRecommendedByUser(similarUser); 
            Set<GameDTO> userVector = new HashSet<>();
            userVector.addAll(recommendedGames);
            userVector.addAll(gamesBySimilarUser);
        
            double numerator = 0;
            for(GameDTO game: userVector) {               
                RecommendationDTO userRecommendation = recommendationFacade.findByAuthorAndGame(user, game);      
                RecommendationDTO similarUserRecommendation = recommendationFacade.findByAuthorAndGame(similarUser, game);       
                 
                if(userRecommendation != null && similarUserRecommendation != null) {
                    if(userRecommendation.isVotedUp() && similarUserRecommendation.isVotedUp()) {
                        numerator += 1;
                    }
                    if(userRecommendation.isVotedUp() && similarUserRecommendation.isVotedUp()) {
                        numerator += 1;
                    }
                }
            }
            
            double coeficient = (2*numerator) / (2*userVector.size());
            coeficient = (2*coeficient) - 1;
            
            if(coeficient > 0 || coeficient < 0) {                
                coeficients.put(similarUser, coeficient);
            }
            
        }
        
        return coeficients;
    }
    
    private GameDTO gameWithLowestRecs(List<GameDTO> games) {
        int min = Integer.MAX_VALUE;
        GameDTO minimalRecsGame = null;
        
        for(GameDTO game : games) {
            int size = recommendationFacade.findByGame(game).size();
            if(size < min) {
                min = size;
                minimalRecsGame = game;
            }
        }
        
        return minimalRecsGame;
    }
    
    private List<UserDTO> similarUsers(List<GameDTO> games, List<UserDTO> allUsers) {
        List<UserDTO> similarUsers = new ArrayList<>();
        
        int counter = 0;
        for(UserDTO anyUser : allUsers) {     
            if(counter == 100) {
                break;
            }
            
            List<GameDTO> anyUserGames = gameFacade.findRecommendedByUser(anyUser);
            
            if(anyUserGames.containsAll(games)) {                
                similarUsers.add(anyUser);
                counter += 1;
            }           
            
        }
        return similarUsers;
    }
    
    private Set<GameDTO> similarUsersGames(Map<UserDTO, Double> coeficients) {
        Set<GameDTO> games = new HashSet<>();
        
        for(UserDTO similarUser: coeficients.keySet()) {
	    Set<RecommendationDTO> recs = new HashSet<>(recommendationFacade.findByAuthor(similarUser));
            
            for(RecommendationDTO rec : recs) {
                games.add(rec.getGame());
            }
        }
        return games;
    }
    
    private Map gamePredictions(Map<UserDTO, Double> coeficients, UserDTO user,
            List<GameDTO> userGames, Set<RecommendationDTO> userRecs) {
        
        double userMean = calculateMean(userRecs);    
        Set<GameDTO> similarUsersGames = similarUsersGames(coeficients);
        similarUsersGames.removeAll(userGames);
        
        Map<GameDTO, Double> gamePredictions = new HashMap<>();
        
        for(GameDTO game : similarUsersGames) {
            double numerator = 0;
            double denominator = 0;
            
            for(UserDTO similarUser: coeficients.keySet()) {
                double coeficient = coeficients.get(similarUser);
                double similarUserMean = calculateMean(new HashSet<>(recommendationFacade.findByAuthor(similarUser)));
               
                RecommendationDTO similarUserRecommendation = recommendationFacade.findByAuthorAndGame(similarUser, game);         
                
                
                if(similarUserRecommendation == null) {
                    continue;
                }
                
                int rating = 0;
                if(similarUserRecommendation.isVotedUp()) {
                    rating = 1;
                }                
                numerator += coeficient*(rating - similarUserMean);
                denominator += coeficient;                
            }        
            
            double prediction = userMean + (numerator/denominator);
            gamePredictions.put(game, prediction);
        }        
        return gamePredictions;
    }
    
    public List<GameDTO> nearestNeighborSubset(long id, boolean pearson) {
        UserDTO user = userFacade.findById(id);
        Set<RecommendationDTO> recsByUser = new HashSet<>(recommendationFacade.findByAuthor(user));
        List<GameDTO> gamesRatedByUser = gameFacade.findRecommendedByUser(user); 
        
        List<UserDTO> allUsers = userFacade.findAll();
        allUsers.remove(user);
        
        List<UserDTO> similarUsers = similarUsers(gamesRatedByUser, allUsers);        
        
        while(similarUsers.size() < 1) {           
            gamesRatedByUser.remove(gameWithLowestRecs(gamesRatedByUser));
            similarUsers = similarUsers(gamesRatedByUser, allUsers);
        }      
        
        if(similarUsers.isEmpty()) {
            return new ArrayList<GameDTO>();
        }
        
        Map<UserDTO, Double> coeficients = new HashMap<>();
        if(pearson) {
           coeficients = pearsonCoeficientMatrix(user, gamesRatedByUser, recsByUser, similarUsers);
        } 
        else {
            coeficients = diceCoeficient(user, gamesRatedByUser, recsByUser, similarUsers);
        }
        
        Map<GameDTO, Double> gamePredictions = gamePredictions(coeficients, user, gamesRatedByUser, recsByUser);
        List<GameDTO> sortedPredictions = (gamePredictions.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).collect(Collectors.toList()));        
        
        sortedPredictions = Lists.reverse(sortedPredictions);   
        return sortedPredictions;
    }
    
    public List<GameDTO> nearestNeighborIntersection(long id, boolean pearson) {
        
        UserDTO user = userFacade.findById(id);
        Set<RecommendationDTO> recsByUser = new HashSet<>(recommendationFacade.findByAuthor(user));
          
        List<GameDTO> gamesRatedByUser = gameFacade.findRecommendedByUser(user);   
        List<UserDTO> allUsers = userFacade.findAll();
        allUsers.remove(user);
        
        Map<UserDTO, Integer> similarUsers = new HashMap();        
        
        int counter = 0;
        for(UserDTO anyUser : allUsers) {
            if(counter == 5) {
                break;
            }
            
            List<GameDTO> anyUserGames = gameFacade.findRecommendedByUser(anyUser);
            anyUserGames.retainAll(gamesRatedByUser);
            similarUsers.put(anyUser, anyUserGames.size());
            
            if(anyUserGames.size() == gamesRatedByUser.size()) {
                counter += 1;
            }            
        }
        
        if(similarUsers.isEmpty()) {
            return new ArrayList<GameDTO>();
        }
        
        List<UserDTO> sortedUsers = (similarUsers.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).collect(Collectors.toList()));
        sortedUsers = Lists.reverse(sortedUsers);   
        
        if(sortedUsers.size() > 5) {
            sortedUsers = sortedUsers.subList(0, 5);
        }        
                
        Map<UserDTO, Double> coeficients = new HashMap<>();
        if(pearson) {
           coeficients = pearsonCoeficientMatrix(user, gamesRatedByUser, recsByUser, sortedUsers);
        } 
        else {
            coeficients = diceCoeficient(user, gamesRatedByUser, recsByUser, sortedUsers);
        }           
        
        Map<GameDTO, Double> gamePredictions = gamePredictions(coeficients, user, gamesRatedByUser, recsByUser);
        List<GameDTO> sortedPredictions = (gamePredictions.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).collect(Collectors.toList()));
        sortedPredictions = Lists.reverse(sortedPredictions);                 
        
        return sortedPredictions;
    }
    
    
}
