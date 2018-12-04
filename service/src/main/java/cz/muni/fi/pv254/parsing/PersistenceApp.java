package cz.muni.fi.pv254.parsing;

import cz.muni.fi.pv254.dto.GenreDTO;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Genre;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
public class PersistenceApp {

    @Autowired
    private App app;

    @Autowired
    private EntityManagerFactory emf;

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public int getOffsetDiff() {
        return offsetDiff;
    }

    public void setOffsetDiff(int offsetDiff) {
        this.offsetDiff = offsetDiff;
    }

    public int getMinReviews() {
        return minReviews;
    }

    public void setMinReviews(int minReviews) {
        this.minReviews = minReviews;
    }

    private int debug = 0;
    private int offsetDiff = 100;
    private int minReviews = 10;

    /**
     * Downloads data from json website and converts them String
     * @param website to download data from
     * @return JSON in string format
     */
    private StringBuffer getJsonFromUrl(String website) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(website);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status != 200) {
                System.out.println(Integer.toString(status));
                throw new HTTPException(status);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        }
        catch (MalformedURLException e) {
            System.out.println(e.toString());
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
        return content;
    }

    public int inteligentParse(long gameID) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Game game;
        try {
            game = em.createQuery("Select game From Game game Where game.steamId = :id",
                    Game.class).setParameter("id", gameID).getSingleResult();

        }
        catch (NoResultException e) {
            game =  null;
        }
        if (game == null) {
            game = new Game();
            game.setSteamId(gameID);
            game.setName(app.downloadGameName(gameID));
            game.setShortDescription(app.downloadShortDescritpion(gameID));
        }
        Set<Genre> genres = parseGenres(game,em);
        game.setGenres(genres);
        Set<Recommendation> recommendations = new HashSet<>();
        int retrievedItems = 0;
        try {
            String url = "https://store.steampowered.com/appreviews/"
                    + Long.toString(gameID) +
                    "?json=1&language=all&num_per_page="
                    +Integer.toString(getOffsetDiff())+
                    "&filter=recent&start_offset=";
            int offset = 0;
            while(true){
                JSONObject obj = new JSONObject(getJsonFromUrl(url + Integer.toString(offset)).toString());
                JSONArray arr = obj.getJSONArray("reviews");
                int oldSize = recommendations.size();
                if (arr.length() == 0) {
                    break;
                }
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject recc = arr.getJSONObject(i);
                    recc = recc.getJSONObject("author");
                    long num_reviews = recc.getLong("num_reviews");
                    retrievedItems++;
                    if (num_reviews >= minReviews) {
                        Recommendation rec = parseRecommendation(arr.getJSONObject(i), game,em);
                        recommendations.add(rec);
                    }
                }

                if (debug >= 2)
                    System.out.println("Retrieved new items with offset "+Integer.toString(offset)+": "+Integer.toString(recommendations.size()-oldSize));
                offset+=getOffsetDiff();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (debug >=1) {
            System.out.println("Stored size: " + Integer.toString(recommendations.size()));
            System.out.println("Retrieved size: "+ Integer.toString(retrievedItems));
            System.out.println("Expected size: "+ Long.toString(app.getTotalNumberOfReviews(gameID)));
        }
        em.persist(game);
        for (Recommendation rec : recommendations) {
            if (rec.getAuthor().getId() == null && rec.getId() == null) {
                em.merge(rec);
                //TODO change to persist and test exceptions
            }
            else{
                em.merge(rec);
            }

        }
        em.getTransaction().commit();
        em.close();
        return recommendations.size();

    }

    private Recommendation parseRecommendation(JSONObject review, Game game, EntityManager em) {
        Long steamId = review.getLong("recommendationid");
        Recommendation rec;
        try {
            rec = em.createQuery("Select rec From Recommendation rec Where rec.steamId = :id",
                    Recommendation.class).setParameter("id", steamId).getSingleResult();

        }
        catch (NoResultException e) {
            rec =  null;
        }
        if (rec == null) {
            Boolean votedUp = review.getBoolean("voted_up");
            Long votesUp = review.getLong("votes_up");
            Double weightedVoteScore = review.getDouble("weighted_vote_score");
            Boolean earlyAccess = review.getBoolean("written_during_early_access");
            User author = parseAuthor(review.getJSONObject("author"),em);
            rec = new Recommendation();
            rec.setGame(game);
            rec.setAuthor(author);
            rec.setSteamId(steamId);
            rec.setEarlyAccess(earlyAccess);
            rec.setVotedUp(votedUp);
            rec.setVotesUp(votesUp);
            rec.setWeightedVoteScore(weightedVoteScore);
//            rec.setGame(game);
//            em.persist(rec);
        }
        return rec;
    }

    private User parseAuthor(JSONObject authorJSON, EntityManager em) {
        Long authorId = authorJSON.getLong("steamid");
        User author;
        try {
            author = em.createQuery("Select user From User user Where user.steamId = :id",
                    User.class).setParameter("id", authorId).getSingleResult();

        }
        catch (NoResultException e) {
            author =  null;
        }
        if (author == null) {
            String authorName = Long.toString(authorId);//downloadUserName(authorId);
            author = new User();
            author.setSteamId(authorId);
            author.setName(authorName);
            author.setEmail(Long.toString(authorId)+"@steam.com");
            author.setIsAdmin(false);
//            em.persist(author);
//            author = userFacade.findById(author.getId());
        }
        return author;
    }

    private Set<Genre> parseGenres(Game game, EntityManager em) {
        long gameID = game.getSteamId();
        Set<Genre> out = new HashSet<>();
        try {
            String url = "https://store.steampowered.com/api/appdetails?appids="+Long.toString(gameID);
            JSONObject obj = new JSONObject(getJsonFromUrl(url).toString());
            obj = obj.getJSONObject(Long.toString(gameID));
            obj = obj.getJSONObject("data");
            JSONArray arr = obj.getJSONArray("genres");
            for (int i = 0; i < arr.length(); i++) {
                String genreName = arr.getJSONObject(i).getString("description");
                Genre genre;
                try {
                    genre = em.createQuery("Select genre From Genre genre Where genre.name = :id",
                            Genre.class).setParameter("id", genreName).getSingleResult();

                }
                catch (NoResultException e) {
                    genre =  null;
                }
                if (genre == null) {
                    genre = new Genre();
                    genre.setName(genreName);
//                    em.persist(genre);
//                    Set<GameDTO> games = genre.getGames();
//                    games.add(game);
//                    genre.setGames(games);
                }
                out.add(genre);
//                RecommendationDTO rec = parseRecommendation(arr.getJSONObject(i),game);
//                recommendations.add(rec);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return out;
    }


}
