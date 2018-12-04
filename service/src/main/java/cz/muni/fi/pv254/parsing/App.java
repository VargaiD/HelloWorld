package cz.muni.fi.pv254.parsing;

import cz.muni.fi.pv254.dto.*;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Genre;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.GenreFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Paths;
import java.util.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * This is class for downloading data from Steam.
 *
 * Note: methods with suffix old were used for testing before using database connection.
 * They are here only for better understanding, they might be deleted later.
 *
 * @author Marek Valko
 */
@Component
public class App
{
    /*
    steamcommunity.com/profiles/USERID
    steamcommunity.com/profiles/USERID/recommended/RECOMENDATIONID
    store.steampowered.com/app/APPID
     */

    @Autowired
    private GameFacade gameFacade;
    @Autowired
    private RecommendationFacade recommendationFacade;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private GenreFacade genreFacade;

    public int getOffsetDiff() {
        return offsetDiff;
    }

    public void setOffsetDiff(int offsetDiff) {
        this.offsetDiff = offsetDiff;
    }

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    /**
     * Level 0 - no print
     * Level 1 - expected, received
     * Level 2 - retrieved items by offset
     * Level 3 - write review ids
     * Level 4 - write user ids
     */
    private int debug = 0;

    private int offsetDiff = 20;

    public int getMinReviews() {
        return minReviews;
    }

    public void setMinReviews(int minReviews) {
        this.minReviews = minReviews;
    }

    private int minReviews = 10;


    private List<Long> gameIds;

    public void addGameId(Long id) {
        if (id == null) {
            throw new NullPointerException("Tried to add game with null id");
        }
        gameIds.add(id);
    }

    public void removeGameId(Long id) {
        if (id == null) {
            throw new NullPointerException("Tried to removen game with id null");
        }
        gameIds.remove(id);
    }
    public List<Long> getGameIds() {
        return Collections.unmodifiableList(gameIds);
    }

    public App() {

        gameIds = new ArrayList<Long>();
    }

    public void createTestDataFoCF(){
        List<UserDTO> users = new ArrayList<>();
        long i = 1;
        for (char a = 'A'; a <= 'J'; a++){
            UserDTO user = new UserDTO();
            user.setName(Character.toString(a));
            user.setSteamId(i);
            user.setEmail(Character.toString(a)+"@steam.com");
            user.setIsAdmin(false);
            i++;
            user = userFacade.add(user,"password");
            users.add(user);
        }
        List<GameDTO> games = new ArrayList<>();
        for (long j = 1;j <=10;j++) {
            GameDTO game = new GameDTO();
            game.setName(Long.toString(j));
            game.setSteamId(j);
            game = gameFacade.add(game);
            games.add(game);
        }
        List<RecommendationDTO> recs = new ArrayList<>();
        long id = 1;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(0), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(1), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(2), true));

        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(3), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(4), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(5), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(0), games.get(6), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(0), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(2), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(3), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1),games.get(4), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(5), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(6), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(1), games.get(7), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(2),games.get(0),true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(2), games.get(1), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(2), games.get(2), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(0), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(2), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(7), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(3), games.get(8), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4),games.get(0),false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(1), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(2), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(6), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(4), games.get(7), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(5), games.get(0), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(5), games.get(2), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(0), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(3), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(4), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(6), games.get(5), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(7), games.get(3), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(7), games.get(5), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(7), games.get(7), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(1), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(2), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(3), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(4), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(8), games.get(5), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(0), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(1), false));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(2), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9), games.get(3), true));
        id++;
        recs.add(new RecommendationDTO(id, users.get(9),games.get(6),false));
        for (RecommendationDTO rec:recs) {
            recommendationFacade.add(rec);
//            userFacade.update(rec.getAuthor());
//            recommendationFacade.update(rec);
        }
    }

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

    /**
     * Get total number of reviews, download that information from web
     * @param gameID id of game to get total number
     * @return total number of reviews
     */
    public int getTotalNumberOfReviews(long gameID) {
        String url = "https://store.steampowered.com/appreviews/" + Long.toString(gameID) + "?json=1&language=all&filter=recent&start_offset=0";
        JSONObject obj = new JSONObject(getJsonFromUrl(url).toString());
        JSONObject summary = obj.getJSONObject("query_summary");
        return summary.getInt("total_reviews");
    }

    /**
     * Download short description for given game
     * @param gameID id of game
     * @return description
     */
    public String downloadShortDescritpion(long gameID) {
        return downloadGameDetails(gameID).get(1);
    }

    /**
     * Downlaod long description for given game
     * @param gameID id of game
     * @return long descrpiton
     */
    public String downloadLongDescription(long gameID) {
        return downloadGameDetails(gameID).get(0);
    }

    /**
     * Download url of picture on steam page for given game
     * @param gameID id of game
     * @return url of the picture
     */
    public String downloadGamePictureUrl(long gameID) {
        return downloadGameDetails(gameID).get(2);
    }

    /**
     * Downloads some details about game
     * @param gameID id of game
     * @return list in format [short description, long description, picture url)
     */
    public List<String> downloadGameDetails(long gameID) {
        List<String> out = new ArrayList<>();
        try {
            String url = "https://store.steampowered.com/api/appdetails?appids="+Long.toString(gameID);
            JSONObject obj = new JSONObject(getJsonFromUrl(url).toString());
            obj = obj.getJSONObject(Long.toString(gameID));
            obj = obj.getJSONObject("data");
            out.add(obj.getString("about_the_game"));
            out.add(obj.getString("short_description"));
            out.add(obj.getString("header_image"));
        }
        catch (Exception e) {
            System.out.println(e.toString());
            out = new ArrayList<>(Arrays.asList("","",""));
        }
        return out;
    }

    /**
     * Loads games from top100.csv file
     * @return true if successful
     */
    public boolean loadTop100() {
        System.out.println(System.getProperty("user.dir"));
        try {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String file = "../../resources/top100.csv";
            List<Long> ids = Load.loadGames(fullPath + file);
            this.gameIds.addAll(ids);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return !getGameIds().isEmpty();
    }

    /**
     * Load top 100 games and downlaod them
     * @return list of numbers, count of downloaded games
     */
    public List<Integer> downloadTop100() {
        if (loadTop100()) {
            return inteligentParseAllGanes();
        }
        return null;
    }

    /**
     * Download genres for given game and store them in database
     * @param game id of the game
     * @return list of genre dtos
     */
    private Set<GenreDTO> parseGenres(GameDTO game) {
        long gameID = game.getSteamId();
        Set<GenreDTO> out = new HashSet<>();
        try {
            String url = "https://store.steampowered.com/api/appdetails?appids="+Long.toString(gameID);
            JSONObject obj = new JSONObject(getJsonFromUrl(url).toString());
            obj = obj.getJSONObject(Long.toString(gameID));
            obj = obj.getJSONObject("data");
            JSONArray arr = obj.getJSONArray("genres");
            for (int i = 0; i < arr.length(); i++) {
                String genreName = arr.getJSONObject(i).getString("description");
//                System.out.println(genre);
                GenreDTO genre = genreFacade.findByName(genreName);
                if (genre == null) {
                    genre = new GenreDTO();
                    genre.setName(genreName);
//                    Set<GameDTO> games = genre.getGames();
//                    games.add(game);
//                    genre.setGames(games);
                    genre = genreFacade.add(genre);
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
    /**
    * Parse description
    */


    public void parseDescrpition(Long id) {


        GameDTO game= gameFacade.findBySteamId(id);
        int TotalSum=0;
        Set<WordDTO> listWords = new HashSet<>();
        Map<String, Integer> words = new HashMap<>();
        ArrayList<String> deleteWord = new ArrayList();
        deleteWord.add("ARE");
       // deleteWord.add("NOT");
        deleteWord.add("AND");
        deleteWord.add("THE");
        deleteWord.add("FOR");
        deleteWord.add("WITH");
        deleteWord.add("BUT");

        String longDescription= downloadLongDescription(game.getSteamId());
        longDescription = stripHtmlRegex(longDescription);
        longDescription = stripTagsCharArray(longDescription);
        //document.getElementsByTagName("H1")[0].removeAttribute("class");
        longDescription=longDescription.replaceAll("&.*?;" , "");
        longDescription=longDescription.replaceAll("[,?:!.]", "");
        longDescription=longDescription.toUpperCase();
        for(String delWord : deleteWord ) {
            longDescription = longDescription.replaceAll(delWord, "");
        }
        longDescription =longDescription.replaceAll("\\s{2,}", " ").trim();

        String a[] = longDescription.split(" ");
        //counting occurance word
        for (String str : a) {
            if(str.length()>2){
                if (words.containsKey(str)) {
                    words.put(str, 1 + words.get(str));
                } else {
                    words.put(str, 1);
                }
            }
        }
        //sort by count word
        words = words
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        TotalSum=a.length;
        for ( Map.Entry<String, Integer> me : words.entrySet()) {
            WordDTO wDTO= new WordDTO();
            wDTO.setGame(game);
            wDTO.setCount((Double.parseDouble(me.getValue().toString()))/TotalSum);
            wDTO.setWord(me.getKey());
            listWords.add(wDTO);
        }
        game.setWords(listWords);
        gameFacade.update(game);


    }

    public String stripHtmlRegex(String source) {
        // Replace all tag characters with an empty string.
        return source.replaceAll("<.*?>", "");
    }

    public String stripTagsCharArray(String source) {
        // Create char array to store our result.
        char[] array = new char[source.length()];
        int arrayIndex = 0;
        boolean inside = false;

        // Loop over characters and append when not inside a tag.
        for (int i = 0; i < source.length(); i++) {
            char let = source.charAt(i);
            if (let == '<') {
                inside = true;
                continue;
            }
            if (let == '>') {
                inside = false;
                continue;
            }
            if (!inside) {
                array[arrayIndex] = let;
                arrayIndex++;
            }
        }
        // ... Return written data.
        return new String(array, 0, arrayIndex);
    }


    /**
     * download and store author info
     * @param authorJSON json part of author
     * @return stored user in userdto format
     */
    private UserDTO parseAuthor(JSONObject authorJSON) {
        Long authorId = authorJSON.getLong("steamid");
        UserDTO author = userFacade.findBySteamId(authorId);
        if (author == null) {
            String authorName = Long.toString(authorId);//downloadUserName(authorId);
            author = new UserDTO();
            author.setSteamId(authorId);
            author.setName(authorName);
            author.setEmail(Long.toString(authorId)+"@steam.com");
            author.setIsAdmin(false);
            author = userFacade.add(author,"password");
//            author = userFacade.findById(author.getId());
        }
        return author;
    }

    /**
     * Download and sotre recommendations for given game
     * @param review review in json format form api
     * @param game game to which the rcommendations will be assigned
     * @return store recommendation in dro format
     */
    private RecommendationDTO parseRecommendation(JSONObject review,GameDTO game) {
        Long steamId = review.getLong("recommendationid");
        RecommendationDTO rec = recommendationFacade.findBySteamId(steamId);
        if (rec == null) {
            Boolean votedUp = review.getBoolean("voted_up");
            Long votesUp = review.getLong("votes_up");
            Double weightedVoteScore = review.getDouble("weighted_vote_score");
            Boolean earlyAccess = review.getBoolean("written_during_early_access");
            UserDTO author = parseAuthor(review.getJSONObject("author"));
            rec = new RecommendationDTO();
            rec.setAuthor(author);
            rec.setSteamId(steamId);
            rec.setEarlyAccess(earlyAccess);
            rec.setVotedUp(votedUp);
            rec.setVotesUp(votesUp);
            rec.setWeightedVoteScore(weightedVoteScore);
//            rec.setGame(game);
            rec = recommendationFacade.add(rec);
        }
        return rec;
    }

    /**
     * Downloads and stores recommendations and their authors for game with gameid.
     * @param gameID steam id of game
     * @return number of downloaded reviews
     */
    public int inteligentParse(long gameID) {
        GameDTO game = gameFacade.findBySteamId(gameID);
        if (game == null) {
            game = new GameDTO();
            game.setSteamId(gameID);
            game.setName(downloadGameName(gameID));
            game.setShortDescription(downloadShortDescritpion(gameID));
            game = gameFacade.add(game);
        }
//        game = gameFacade.findBySteamId(game.getSteamId());
        Set<GenreDTO> genres = parseGenres(game);
        game.setGenres(genres);
        gameFacade.update(game);

        if (debug >= 1) {
            System.out.println(game.getName());
        }
        Set<RecommendationDTO> recommendations = new HashSet<>();
        int retrievedItems = 0;
        try {
            String url = "https://store.steampowered.com/appreviews/"
                    + Long.toString(gameID) +
                    "?json=1&language=all&num_per_page="
                    +Integer.toString(getOffsetDiff())+
                    "&filter=recent&start_offset=";
            int offset = 0;
            while (true) {
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
//                    System.out.println(num_reviews);
                    retrievedItems++;
                    if (num_reviews >= minReviews) {
                        RecommendationDTO rec = parseRecommendation(arr.getJSONObject(i), game);
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
//        game.setRecommendations(recommendations);
        for (RecommendationDTO r : recommendations) {
            r.setGame(game);
            recommendationFacade.update(r);
        }


        if (debug >=1) {
            System.out.println("Stored size: " + Integer.toString(recommendations.size()));
            System.out.println("Retrieved size: "+ Integer.toString(retrievedItems));
            System.out.println("Expected size: "+ Long.toString(getTotalNumberOfReviews(gameID)));
        }
        if (debug >= 3) {
            ArrayList<Long> sortedRecIds = new ArrayList<>();
            ArrayList<Long> sortedAuthorIds = new ArrayList<>();
            for (RecommendationDTO rec : recommendations) {
                sortedRecIds.add(rec.getId());
                if (debug >= 4) {
                    sortedAuthorIds.add(rec.getAuthor().getId());
                }
            }
            if (debug >= 4) {
                Collections.sort(sortedAuthorIds);
                for (Long id : sortedAuthorIds) {
                    System.out.println(id.toString()+" = "+ userFacade.findBySteamId(id).getName());
                }
            }
            Collections.sort(sortedRecIds);
            System.out.println("ReviewIds");
            for (Long id : sortedRecIds) {
                System.out.println(id.toString()+" = "+recommendationFacade.findBySteamId(id).isVotedUp());
            }
        }
        return recommendations.size();

    }

    /**
     * Downlaod reviews for all games stored in this entity
     * @return list of numbers of reviews downloaded
     */
    public List<Integer> inteligentParseAllGanes()  {
        List<Integer> sizes = new ArrayList<>();
        for (Long id : gameIds) {
            sizes.add(inteligentParse(id));
            // TODO remove downloaded game from gameids
        }
        return sizes;
    }

    public void downloadGameOnly(long gameID) {
        GameDTO game = gameFacade.findBySteamId(gameID);
        if(debug >=1) {
            System.out.println("Downloading game only " + gameID);
        }
        if (game == null) {
            game = new GameDTO();
            game.setSteamId(gameID);
            game.setName(downloadGameName(gameID));
            game.setShortDescription(downloadShortDescritpion(gameID));
            game = gameFacade.add(game);
        }
        Set<GenreDTO> genres = parseGenres(game);
        game.setGenres(genres);
        gameFacade.update(game);
    }

    public void downloadAllGamesOnly() {
        for (Long id : gameIds) {
            downloadGameOnly(id);
            try {
                Thread.sleep(2000L);
            }
            catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }

    }

    /**
     * Download reviews for given gameid
     *
     * @param gameID id of game
     * @return set of lists, in listst there is recid, author id and voted up
     */
    public Set<List<Object>> inteligentParseOld(long gameID) {
        Set<List<Object>> recIds = new HashSet<>();
        try {
            String url = "https://store.steampowered.com/appreviews/"
                    + Long.toString(gameID) +
                    "?json=1&language=all&num_per_page="
                    +Integer.toString(getOffsetDiff())+
                    "&filter=recent&start_offset=";
            int offset = 0;
            while (true) {
                // TODO check success code
                JSONObject obj = new JSONObject(getJsonFromUrl(url + Integer.toString(offset)).toString());
                JSONArray arr = obj.getJSONArray("reviews");
                int oldSize = recIds.size();
                if (arr.length() == 0) {
                    break;
                }
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject review = arr.getJSONObject(i);
                    Long id = review.getLong("recommendationid");
                    JSONObject author = review.getJSONObject("author");
                    String userid = author.getString("steamid");
                    userid = userid.replaceAll("\"","");
                    Long userId = Long.parseLong(userid);
                    Boolean votedUp = review.getBoolean("voted_up");
                    List<Object> list = new ArrayList<>();
                    list.add(id);
                    list.add(userId);
                    list.add(votedUp);
                    recIds.add(list);
                }
                if (debug >= 2)
                    System.out.println("Retrieved new items with offset "+Integer.toString(offset)+": "+Integer.toString(recIds.size()-oldSize));
                offset+=getOffsetDiff();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (debug >=1) {
            System.out.println("Received size: "+Integer.toString(recIds.size()));
            System.out.println("Expected size: "+ Long.toString(getTotalNumberOfReviews(gameID)));
        }
        if (debug >=3) {

            ArrayList<Long> sorted = new ArrayList<>();
            System.out.println("UserIds");
            for (List<Object> object : recIds) {
                sorted.add((Long) object.get(0));
                if (debug >= 4) {
                    System.out.println(object.get(1) +" = " +downloadUserName((Long)object.get(1)));

                }
            }
            Collections.sort(sorted);
            System.out.println("ReviewIds");
            for (Long id : sorted) {
                System.out.println(id.toString());
            }
        }
        return recIds;

    }

    /**
     * Downlaods name of the game from web
     * @param gameId id of game
     * @return Name of the game
     */
    public String downloadGameName(long gameId) {
        String name = "";
        try {
            String url = "https://store.steampowered.com/api/appdetails?appids="+Long.toString(gameId);
            JSONObject obj = new JSONObject(getJsonFromUrl(url).toString());
            obj = obj.getJSONObject(Long.toString(gameId));
            obj = obj.getJSONObject("data");
            name = obj.getString("name");
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return name;
    }

    /**
     * Downloads username for given id from web
     * @param userId id of user
     * @return username
     */
    public String downloadUserName(long userId) {
        String url = "https://steamcommunity.com/profiles/" + Long.toString(userId);
        String name = "";
        int tries = 0;
        while(true) {
            try {
                Document doc = Jsoup.connect(url).get();
                Element body = doc.body();
                Elements nieco = body.getElementsByAttributeValue("class","actual_persona_name");
                if (nieco.isEmpty()) {
                    // TODO tu to niekedy pada ze nie je strana dostupna
                    // TODO aj tu to treba zopakovat {tries}
                    throw new IllegalArgumentException("User Name not found for id "+Long.toString(userId));
                }
                name = nieco.get(0).text();
                return name;
            }
            // sometimes it throws this exception, try at least 3 times before exiting
            catch (SocketTimeoutException e) {
                if (tries >= 3) {
                    return name;
                }
                tries++;
            }
            catch (IOException e) {
                System.out.println(e.toString());
            }

        }
    }

    /**
     * Parse all games stored in gameIds list
     * it uses old parsing method
     */
    public void inteligentParseAllGanesOld()  {
        for (Long id : gameIds) {
            inteligentParseOld(id);
        }
    }

    public static void main( String[] args ) throws InterruptedException
    {
//        int[] games = {892760, 911520, 964030,717690,949970,893330,396900};
//        int[] games = {396900,582010,292030};
        int index = 6;
        int[] games = {717690};
        App app = new App();
        app.setOffsetDiff(100);
        app.setDebug(4);
        System.out.println(app.downloadLongDescription(360430L));
//  for (int id : games) {
//            System.out.println(app.downloadGameName(id));
//            System.out.println(app.getTotalNumberOfReviews(id));
//            for (int i = 0 ; i< 1 ; i++) { // DO it more times
////                Thread.sleep(10000); // wait for 10 seconds, so steam wont block us
//                app.inteligentParseOld(id);
//
//            }
//        }
//        GameDTO game = new GameDTO();
//        game.setSteamId(57690L);
//        System.out.println(app.parseGenres(game));

        // user wraith 'Wraith_Skyline' id 76561197994264572
        // made revies on games
        // 223810 - Ys I & II Chronicles+ - vela recenzii
        // 530320 -Wandersong
        // 896460 - Lucah: Born of a Dream
        // 837330 - Crimson Shift
        // 688420 - Bad North
        // 868520 - killer7
        // 658690 - Rage in Peace


    }


}
