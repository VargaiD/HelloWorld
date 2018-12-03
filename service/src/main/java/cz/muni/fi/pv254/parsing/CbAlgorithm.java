package cz.muni.fi.pv254.parsing;

import cz.muni.fi.pv254.dto.*;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.GenreFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
public class CbAlgorithm {
    public class CBentityAB {
        private GameDTO gameA;
        private GameDTO gameB;
        private double scoreAB;


        public GameDTO getGameA() {
            return gameA;
        }

        public void setGameA(GameDTO gameA) {
            this.gameA = gameA;
        }

        public GameDTO getGameB() {
            return gameB;
        }

        public void setGameB(GameDTO gameB) {
            this.gameB = gameB;
        }

        public double getScoreAB() {
            return scoreAB;
        }

        public void setScoreAB(double scoreAB) {
            this.scoreAB = scoreAB;
        }


    }


    @Autowired
    private GameFacade gameFacade;
    @Autowired
    private RecommendationFacade recommendationFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private GenreFacade genreFacade;

    /**
     *  https://www.linkedin.com/pulse/content-based-recommender-engine-under-hood-venkat-raman
     *  https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=6838200

     * use this altgorithm  https://medium.com/@adriensieg/text-similarities-da019229c894?fbclid=IwAR1bxMdKdAWh7mAWTnAi1dQQV_q5v69lqudwUsypni3773dj-4W7sDscYf4
     * hraA----hodnotena.....hraB---nehodnotena......pre kazde slovo z hry A najdem slovo v hreB...ak tam je vynasobim ich count a dalej scitavam cv citateli.......menovatel vsetky slova
     * @param userDTO
     * @return
     */
    private Set<GameDTO> recommendationByWord(UserDTO userDTO){

        Set<GameDTO> userGames= new HashSet<>();
        Set<GameDTO> VoteUserGames= new HashSet<>();
        Set<GameDTO> bestScore = new HashSet<>();
        Map<String, Double> abScore = new HashMap<>();
        for(RecommendationDTO reDTO: userDTO.getRecommendations()){
                if(reDTO.isVotedUp()){
                    VoteUserGames.add(reDTO.getGame());
                }
                userGames.add(reDTO.getGame());

        }
        List<GameDTO> notRecUserGames =gameFacade.findAll();
        for(GameDTO game: userGames){
            notRecUserGames.remove(game);
        }
        //hodnotena hra
        for(GameDTO Votegame: VoteUserGames){
            Double ScoreSumAB=0D;
            for(GameDTO NotVotegame: notRecUserGames){
                Set<WordDTO> voteWords= Votegame.getWords();
                Set<WordDTO> NotVoteWords= NotVotegame.getWords();
                for(WordDTO Voteword: voteWords){
                    for(WordDTO NotVoteword: NotVoteWords){
                        if(Voteword.getWord().equals(NotVoteword.getWord())){
                            ScoreSumAB=ScoreSumAB+(Voteword.getCount()*NotVoteword.getCount());
                        }
                    }
                }
                CBentityAB cBentityAB=new CBentityAB();
                cBentityAB.setGameA(Votegame);
                //nehodnotene
                cBentityAB.setGameB(NotVotegame);
                cBentityAB.setScoreAB(ScoreSumAB/(Math.sqrt(scoreA(Votegame)*scoreA(NotVotegame))*Math.sqrt(scoreA(Votegame)*scoreA(Votegame))));
                abScore.put(cBentityAB.getGameA().getId()+","+cBentityAB.getGameB().getId(), cBentityAB.getScoreAB());

            }
        }
        //sorted by
        abScore = abScore
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        //return only 5 games with best score
        for ( Map.Entry<String, Double> me : abScore.entrySet()) {
            if(bestScore.size()<6) {
                String id = me.getKey();
                String a[] = id.split(",");
                GameDTO game = gameFacade.findById(Long.parseLong(a[1]));
                bestScore.add(game);
            }
        }


        return bestScore;
    }


    private Double scoreA(GameDTO game){
        Double ScoreSumA=0D;
        Set<WordDTO> gameWords= game.getWords();
        for(WordDTO gam: gameWords){
           ScoreSumA=ScoreSumA+gam.getCount();
        }
        return ScoreSumA;
    }

    private Set<GameDTO> recommendationByTag(UserDTO userDTO){
        Set<GameDTO> userGames= new HashSet<>();
        Set<GameDTO> VoteUserGames= new HashSet<>();
        Set<GameDTO> bestScore = new HashSet<>();
        Map<String, Integer> abScore = new HashMap<>();
        Map<Long, Integer> countIntersection = new HashMap<>();
        for(RecommendationDTO reDTO: userDTO.getRecommendations()){
            if(reDTO.isVotedUp()){
                VoteUserGames.add(reDTO.getGame());
            }
            userGames.add(reDTO.getGame());

        }
        List<GameDTO> notRecUserGames =gameFacade.findAll();
        for(GameDTO game: userGames){
            notRecUserGames.remove(game);
        }
        //hodnotena hra
        for(GameDTO Votegame: VoteUserGames) {
            Double ScoreSumAB = 0D;
            for (GameDTO NotVotegame : notRecUserGames) {
                CBentityAB cBentityAB=new CBentityAB();
                cBentityAB.setGameA(Votegame);
                //nehodnotene
                cBentityAB.setGameB(NotVotegame);
                Set intersectGame = new HashSet();
                intersectGame.add(Votegame.getGenres().retainAll(NotVotegame.getGenres()));
                abScore.put(cBentityAB.getGameA().getId()+","+cBentityAB.getGameB().getId(), intersectGame.size());

            }
        }

        //sorted by
        abScore = abScore
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        //return only 5 games with best score
        for ( Map.Entry<String, Integer> tag : abScore.entrySet()) {
            if(bestScore.size()<=5) {
                String id = tag.getKey();
                String a[] = id.split(",");
                GameDTO game = gameFacade.findById(Long.parseLong(a[1]));
                bestScore.add(game);
            }
        }


        return bestScore;
    }


}
