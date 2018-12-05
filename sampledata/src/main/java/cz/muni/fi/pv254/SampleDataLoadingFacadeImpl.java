package cz.muni.fi.pv254;

import cz.muni.fi.pv254.dao.UserDao;
import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.RecommendationDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import cz.muni.fi.pv254.parsing.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {


    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private GameFacade gameFacade;

    @Autowired
    private RecommendationFacade recommendationFacade;

    @Autowired
    private App app;

    @Override
    public void loadData() throws IOException {
//        User admin = user(123L,"password", "Admin", "admin@google.com", "766766766", "Praha", Boolean.TRUE);
//        User user = user(124L, "password", "User", "user@google.com", "755755755", "Olomouc", Boolean.FALSE);
//        System.out.println(userFacade.findById(user.getId()).toString());
//        System.out.println(userFacade.findById(3L));
//        System.out.println(userFacade.findBySteamId(124L).toString());
//        System.out.println(userFacade.findBySteamId(125L));
        //  TODO test game
//        app.setDebug(1);
//        app.setOffsetDiff(100);
//        app.addGameId(892760L);
//        app.addGameId(911520L);
//        app.addGameId(964030L);
////        app.inteligentParse(892760L);
//        List<Integer> sizes = app.inteligentParseAllGanes();
//        System.out.println(sizes);
//        for (GameDTO game : gameFacade.findAll()) {
//            if (game != null) {
//                System.out.println(game.toString());
//            }
//        }
//        for (UserDTO user : userFacade.findAll()) {
//            if (user != null) {
//                System.out.println(user.toString());
//            }
//        }
//        for (RecommendationDTO rec : recommendationFacade.findAll()) {
//            if (rec != null) {
//                System.out.println(rec.toString());
//            }
//        }


        /**
         * Old way of downloading/testing
         */
        if (false) {
            app = new App();
            app.setDebug(0);
            app.setOffsetDiff(100);
//        int[] games = {892760, 911520, 964030,717690,949970,893330,396900,396900,582010,292030};
            long[] games = {892760L};
            for (long id : games) {
                Game game = game(id, app.downloadGameName(id));
                Set<List<Object>> ret = app.inteligentParseOld(id);
                for (List<Object> rec : ret) {

                    Long userIdInt = (Long) rec.get(1);
//                String userIdString = Long.toString((Long)rec.get(1));
                    String userIdString = app.downloadUserName(userIdInt);
                    String userIdEmail = userIdString + "@" + Long.toString(userIdInt) + ".com";
                    System.out.println(userIdString);
                    User author = user(userIdInt, userIdString, userIdString, userIdEmail, Boolean.FALSE);
                    Long recId = (Long) rec.get(0);
                    Boolean recVotedUp = (Boolean) rec.get(2);
                    Recommendation recommendation = recommendation(recId, recVotedUp, game, author);
                }

            }
        }
    }

    private Recommendation recommendation(Long steamId, boolean votedUp, Game game, User user) {
        Recommendation r = new Recommendation();
        r.setSteamId(steamId);
        r.setVotedUp(votedUp);
        r.setAuthor(user);
        r.setGame(game);
        recommendationService.add(r);
        return r;
    }

    private User user(long steamId, String password, String name, String email, Boolean isAdmin) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setIsAdmin(isAdmin);
        u.setSteamId(steamId);
        User existing = userService.findByEmail(email);
        if (existing == null)
            userService.registerUser(u, password);
        return u;
    }

    private Game game(Long steamId, String name){
        Game g = new Game();
        g.setSteamId(steamId);
        g.setName(name);

        Game existing = gameService.findByName(name);
        if (existing == null)
            gameService.add(g);
        return g;
    }
}