/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.cz.pv254.algorithms;

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


/**
 *
 * @author Šimon Baláž
 */
public class CollaborativeFiltering {
    
    @Autowired
    private static GameFacade gameFacade;
    @Autowired
    private static RecommendationFacade recommendationFacade;
    @Autowired
    private static UserFacade userFacade;
    @Autowired
    private static GenreFacade genreFacade;
    
    static List<UserDTO> users = new ArrayList<>();
    static List<GameDTO> games = new ArrayList<>();
    static List<RecommendationDTO> recs = new ArrayList<>();
    
    public static void createTestData(){        
        long i = 1;
        for (char a = 'A'; a <= 'J'; a++){
            UserDTO user = new UserDTO();
            user.setName(Character.toString(a));
            user.setSteamId(i);
            user.setEmail(Character.toString(a)+"@steam.com");
            user.setIsAdmin(false);
            i++;
//            user = userFacade.add(user,"password");
            users.add(user);
        }        
        
        for (long j = 1;j <=10;j++) {
            GameDTO game = new GameDTO();
            game.setName(Long.toString(j));
            game.setSteamId(j);
//            game = gameFacade.add(game);
            games.add(game);
        }
                
        long id = 1;        
        Set<RecommendationDTO> recs0 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(0), games.get(0), true));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(1), false));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(2), true));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(3), true));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(4), false));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(5), true));
        recs0.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(6), false));
        recs0.add(recs.get(((int)id) - 1));
        users.get(0).setRecommendations(recs0);
        id++;
        
        Set<RecommendationDTO> recs1 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(1), games.get(0), false));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(2), true));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(3), false));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1),games.get(4), true));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(5), true));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(6), false));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(7), true));
        recs1.add(recs.get(((int)id) - 1));
        id++;
        users.get(1).setRecommendations(recs1);
        
        Set<RecommendationDTO> recs2 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(2),games.get(0),true));
        recs2.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(2), games.get(1), true));
        recs2.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(2), games.get(2), true));
        recs2.add(recs.get(((int)id) - 1));
        id++;
        users.get(2).setRecommendations(recs2);
        
        Set<RecommendationDTO> recs3 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(3), games.get(0), true));
        recs3.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(2), false));
        recs3.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(7), true));
        recs3.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(8), true));
        recs3.add(recs.get(((int)id) - 1));
        id++;
        users.get(3).setRecommendations(recs3);
        
        Set<RecommendationDTO> recs4 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(4),games.get(0),false));
        recs4.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(1), true));
        recs4.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(2), false));
        recs4.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(6), true));
        recs4.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(7), true));
        recs4.add(recs.get(((int)id) - 1));
        id++;
        users.get(4).setRecommendations(recs4);
        
        Set<RecommendationDTO> recs5 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(5), games.get(0), false));
        recs5.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(5), games.get(2), true));
        recs5.add(recs.get(((int)id) - 1));
        id++;
        users.get(5).setRecommendations(recs5);
        
        Set<RecommendationDTO> recs6 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(6), games.get(0), false));
        recs6.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(3), false));
        recs6.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(4), false));
        recs6.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(5), false));
        recs6.add(recs.get(((int)id) - 1));
        id++;
        users.get(6).setRecommendations(recs6);
        
        Set<RecommendationDTO> recs7 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(7), games.get(3), true));
        recs7.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(7), games.get(5), true));
        recs7.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(7), games.get(7), true));
        recs7.add(recs.get(((int)id) - 1));
        id++;
        users.get(7).setRecommendations(recs7);
        
        Set<RecommendationDTO> recs8 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(8), games.get(1), true));
        recs8.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(2), false));
        recs8.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(3), false));
        recs8.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(4), false));
        recs8.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(5), false));
        recs8.add(recs.get(((int)id) - 1));
        id++;
        users.get(8).setRecommendations(recs8);
        
        Set<RecommendationDTO> recs9 = new HashSet<>();
        recs.add(new RecommendationDTO(id, users.get(9), games.get(0), true));
        recs9.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(1), false));
        recs9.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(2), true));
        recs9.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(3), true));
        recs9.add(recs.get(((int)id) - 1));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9),games.get(6),false));
        recs9.add(recs.get(((int)id) - 1));
        users.get(9).setRecommendations(recs9);
 //       for (RecommendationDTO rec:recs) {
//            recommendationFacade.add(rec);
//            userFacade.update(rec.getAuthor());
//            recommendationFacade.update(rec);
//        }      
        
        
       
    }
    
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
    
    private static Map pearsonCoeficientMatrix(UserDTO user, List<GameDTO> recommendedGames, 
            Set<RecommendationDTO> recommendations, List<UserDTO> sortedUsers) {
        
        Map<UserDTO, Double> coeficients = new HashMap(); 
        
        double userMean = calculateMean(recommendations);        
        
        for(UserDTO similarUser : sortedUsers) {
            double similarUserMean = calculateMean(similarUser.getRecommendations());
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
    
    private static Map diceCoeficient(UserDTO user, List<GameDTO> recommendedGames, 
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
    
    private static GameDTO gameWithLowestRecs(List<GameDTO> games) {
        int min = Integer.MAX_VALUE;
        GameDTO minimalRecsGame = null;
        
        for(GameDTO game : games) {
            int size = game.getRecommendations().size();           
            if(size < min) {
                min = size;
                minimalRecsGame = game;
            }
        }
        
        return minimalRecsGame;
    }
    
    private static List<UserDTO> similarUsers(List<GameDTO> games, List<UserDTO> allUsers) {
        List<UserDTO> similarUsers = new ArrayList<>();
        
        for(UserDTO anyUser : allUsers) {                        
            List<GameDTO> anyUserGames = gameFacade.findRecommendedByUser(anyUser);
            
            if(anyUserGames.containsAll(games)) {                
                similarUsers.add(anyUser);
            }            
        }
        return similarUsers;
    }
    
    private static Set<GameDTO> similarUsersGames(Map<UserDTO, Double> coeficients) {
        Set<GameDTO> games = new HashSet<>();
        
        for(UserDTO similarUser: coeficients.keySet()) {
	    Set<RecommendationDTO> recs = similarUser.getRecommendations();            
            
            for(RecommendationDTO rec : recs) {
                games.add(rec.getGame());
            }
        }
        return games;
    }
    
    private static Map gamePredictions(Map<UserDTO, Double> coeficients, UserDTO user,
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
                double similarUserMean = calculateMean(similarUser.getRecommendations());                                
               
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
    
    public static List<GameDTO> nearestNeighborSubset(long steamId, boolean pearson) {       
        UserDTO user = userFacade.findBySteamId(steamId);        
        Set<RecommendationDTO> recsByUser = user.getRecommendations();          
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
    
    public static List<GameDTO> nearestNeighborIntersection(long steamId, boolean pearson) {
        
        UserDTO user = userFacade.findBySteamId(steamId);        
        Set<RecommendationDTO> recsByUser = user.getRecommendations();     
          
        List<GameDTO> gamesRatedByUser = gameFacade.findRecommendedByUser(user);   
        List<UserDTO> allUsers = userFacade.findAll();
        allUsers.remove(user);
        
        Map<UserDTO, Integer> similarUsers = new HashMap();        
        
        for(UserDTO anyUser : allUsers) {      
            Set<RecommendationDTO> anyUserRecs = anyUser.getRecommendations();            
            List<GameDTO> anyUserGames = gameFacade.findRecommendedByUser(anyUser);
            anyUserGames.retainAll(gamesRatedByUser);
            similarUsers.put(anyUser, anyUserGames.size());            
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
