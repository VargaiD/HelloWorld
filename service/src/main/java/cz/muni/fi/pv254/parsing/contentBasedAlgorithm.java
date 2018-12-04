package cz.muni.fi.pv254.parsing;

import cz.muni.fi.pv254.dto.*;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.GenreFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toMap;

@Component
@Transactional
public class contentBasedAlgorithm {
    public class contentBasedEntityAB {
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
    public Set<GameDTO> recommendationByWord(UserDTO userDTO){

        Set<GameDTO> userGames= new HashSet<>();
        Set<GameDTO> VoteUserGames= new HashSet<>();
        Set<GameDTO> bestScore = new HashSet<>();
        Map<String, Double> abScore = new HashMap<>();
        Map<String, GameDTO> uniqABScore = new HashMap<>();
        for(RecommendationDTO reDTO: userDTO.getRecommendations()){
                if(reDTO.isVotedUp()){
                    VoteUserGames.add(reDTO.getGame());
                }
                userGames.add(reDTO.getGame());

        }
        Map<Long, GameDTO> notRecUserGames2 = new HashMap<>();
        for (GameDTO g : gameFacade.findAll()){
            notRecUserGames2.put(g.getId(),g);
        }
        for(GameDTO g:userGames){
           try {
               notRecUserGames2.remove(g.getId());
           }
           catch (Exception e){}
        }
        List<GameDTO> notRecUserGames= new ArrayList<>();
        for (  Map.Entry<Long, GameDTO> not : notRecUserGames2.entrySet()) {
            notRecUserGames.add(not.getValue());
        }

        //Recommendation game
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
                contentBasedEntityAB cBentityAB=new contentBasedEntityAB();
                cBentityAB.setGameA(Votegame);
                //NotRecommendation game
                cBentityAB.setGameB(NotVotegame);
                cBentityAB.setScoreAB(ScoreSumAB/(scoreA(Votegame)*scoreA(NotVotegame)));
                abScore.put(cBentityAB.getGameA().getId()+","+cBentityAB.getGameB().getId(), cBentityAB.getScoreAB());

            }
        }
        //sort
        abScore = abScore
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        //return only 5 games with best score

        for ( Map.Entry<String, Double> me : abScore.entrySet()) {
            if(bestScore.size()<5) {
                String id = me.getKey();
                String a[] = id.split(",");
                GameDTO game = gameFacade.findById(Long.parseLong(a[1]));
                //unique
                if(!uniqABScore.containsKey(a[1])) {
                    if(Double.POSITIVE_INFINITY>me.getValue()){
                        uniqABScore.put(a[1],game);
                        bestScore.add(game);
                    }
                }

            }
            else break;
        }
        return bestScore;
    }


    private Double scoreA(GameDTO game){
        Double ScoreSumA=0D;
        Set<WordDTO> gameWords= game.getWords();
        for(WordDTO gam: gameWords){
           ScoreSumA=ScoreSumA+(gam.getCount()*gam.getCount());
        }
        return Math.sqrt(ScoreSumA);
    }

    public Set<GameDTO> recommendationFrequentByTag(UserDTO userDTO) {
        Set<GameDTO> userGames = new HashSet<>();
        Set<GameDTO> VoteUserGames = new HashSet<>();
        Set<GameDTO> bestScore = new HashSet<>();
        Map<String, Integer> abScore = new HashMap<>();
        Map<String, GameDTO> uniqABScore = new HashMap<>();
        Map<Long, Integer> countIntersection = new HashMap<>();
        Map<String, Integer> countTag = new HashMap<>();
        for (RecommendationDTO reDTO : userDTO.getRecommendations()) {
            if (reDTO.isVotedUp()) {
                VoteUserGames.add(reDTO.getGame());
            }
            userGames.add(reDTO.getGame());

        }
        Map<Long, GameDTO> notRecUserGames2 = new HashMap<>();
        for (GameDTO g : gameFacade.findAll()) {
            notRecUserGames2.put(g.getId(), g);
        }
        for (GameDTO g : userGames) {
            try {
                notRecUserGames2.remove(g.getId());
            } catch (Exception e) {
            }
        }
        List<GameDTO> notRecUserGames = new ArrayList<>();
        for (Map.Entry<Long, GameDTO> not : notRecUserGames2.entrySet()) {
            notRecUserGames.add(not.getValue());
        }
        ///frequent by genres
        for (GameDTO Votegame : VoteUserGames) {
            for (GenreDTO genre : Votegame.getGenres()) {
                if (countTag.containsKey(genre.getName())) {
                    countTag.put(genre.getName(), 1 + countTag.get(genre.getName()));
                } else {
                    countTag.put(genre.getName(), 1);
                }
            }
        }
        //count frequent tag
        countTag = countTag
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));


        //Recommendation game
        for (GameDTO Votegame : VoteUserGames) {
            int ScoreSumAB=0;
            for (GameDTO NotVotegame : notRecUserGames) {
                contentBasedEntityAB cBentityAB = new contentBasedEntityAB();
                cBentityAB.setGameA(Votegame);
                //NotRecommendation game
                cBentityAB.setGameB(NotVotegame);
                Set<GenreDTO> voteGenre= Votegame.getGenres();
                Set<GenreDTO> NotVoteGenre= NotVotegame.getGenres();
                for(GenreDTO genre :voteGenre){
                    for(GenreDTO genreNotvote :NotVoteGenre){
                        if(genre.getName().equals(genreNotvote.getName())){
                            ScoreSumAB=ScoreSumAB+(countTag.get(genreNotvote.getName()));
                          //  ScoreSumAB=ScoreSumAB+(countTag.get(genreNotvote.getName())*countTag.get(genreNotvote.getName()));
                         //   System.out.println(ScoreSumAB+""+genre.getName()+" "+genreNotvote.getName());
                        }
                    }
                }

                abScore.put(cBentityAB.getGameA().getId() + "," + cBentityAB.getGameB().getId(), ScoreSumAB);
                ScoreSumAB=0;
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
            for (Map.Entry<String, Integer> tag : abScore.entrySet()) {
                if (bestScore.size() < 5) {
                    String id = tag.getKey();
                    String a[] = id.split(",");
                    GameDTO game = gameFacade.findById(Long.parseLong(a[1]));
                    if (!uniqABScore.containsKey(a[1])) {
                        if (Double.POSITIVE_INFINITY > tag.getValue()) {
                            uniqABScore.put(a[1], game);
                            bestScore.add(game);
                        }
                    }
                } else break;
            }


            return bestScore;
        }



    public Set<GameDTO> recommendationByTag(UserDTO userDTO) {
        Set<GameDTO> userGames = new HashSet<>();
        Set<GameDTO> VoteUserGames = new HashSet<>();
        Set<GameDTO> bestScore = new HashSet<>();
        Map<String, Integer> abScore = new HashMap<>();
        Map<String, GameDTO> uniqABScore = new HashMap<>();
        Map<Long, Integer> countIntersection = new HashMap<>();
        Map<String, Integer> countTag = new HashMap<>();
        for (RecommendationDTO reDTO : userDTO.getRecommendations()) {
            if (reDTO.isVotedUp()) {
                VoteUserGames.add(reDTO.getGame());
            }
            userGames.add(reDTO.getGame());

        }
        Map<Long, GameDTO> notRecUserGames2 = new HashMap<>();
        for (GameDTO g : gameFacade.findAll()) {
            notRecUserGames2.put(g.getId(), g);
        }
        for (GameDTO g : userGames) {
            try {
                notRecUserGames2.remove(g.getId());
            } catch (Exception e) {
            }
        }
        List<GameDTO> notRecUserGames = new ArrayList<>();
        for (Map.Entry<Long, GameDTO> not : notRecUserGames2.entrySet()) {
            notRecUserGames.add(not.getValue());
        }
        //Recommendation game

        for (GameDTO Votegame : VoteUserGames) {
            int ScoreSumAB=0;
            for (GameDTO NotVotegame : notRecUserGames) {
                contentBasedEntityAB cBentityAB = new contentBasedEntityAB();
                cBentityAB.setGameA(Votegame);
                //NotRecommendation game
                cBentityAB.setGameB(NotVotegame);
                Set<GenreDTO> voteGenre= Votegame.getGenres();
                Set<GenreDTO> NotVoteGenre= NotVotegame.getGenres();
                for(GenreDTO genre :voteGenre){
                    for(GenreDTO genreNotvote :NotVoteGenre){
                        if(genre.getName().equals(genreNotvote.getName())){
                          //  System.out.println(genre.getName()+"das"+genreNotvote.getName()+" "+ScoreSumAB);
                            ScoreSumAB=ScoreSumAB+1;
                        }
                    }
                }
                abScore.put(cBentityAB.getGameA().getId() + "," + cBentityAB.getGameB().getId(),ScoreSumAB);
                ScoreSumAB=0;
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
        for (Map.Entry<String, Integer> tag : abScore.entrySet()) {
            if (bestScore.size() < 5) {
                String id = tag.getKey();
                String a[] = id.split(",");
                GameDTO game = gameFacade.findById(Long.parseLong(a[1]));
                if (!uniqABScore.containsKey(a[1])) {
                    if (Double.POSITIVE_INFINITY > tag.getValue()) {
                        uniqABScore.put(a[1], game);
                        bestScore.add(game);
                    }
                }
            } else break;
        }


        return bestScore;
    }




    }
