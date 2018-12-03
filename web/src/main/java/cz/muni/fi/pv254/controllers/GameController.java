package cz.muni.fi.pv254.controllers;

import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.RecommendationDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.exceptions.ResourceNotFoundException;
import cz.muni.fi.pv254.facade.GameFacade;
import cz.muni.fi.pv254.facade.RecommendationFacade;
import cz.muni.fi.pv254.facade.UserFacade;
import cz.muni.fi.pv254.parsing.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/game")
public class GameController {

    final static Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private App app;

    @Autowired
    private GameFacade gameFacade;

    @Autowired
    private RecommendationFacade recommendationFacade;

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String Index(Model model,
                       HttpServletRequest req,
                       HttpServletResponse res){

        return "/game/download";
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public String Download(@RequestParam String steamId,
                           @RequestParam int minReviews,
                           RedirectAttributes redirectAttributes){

        return "/game/download";
    }



    @RequestMapping(value = "/rate/{step}", method = RequestMethod.GET)
    public String rateGames(
            @PathVariable("step") int step,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes){

        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if (authUser != null){

            UserDTO user = userFacade.findById(authUser.getId());
            Set<RecommendationDTO> recommendations = user.getRecommendations();

            if (recommendations.size() >= 10){
                redirectAttributes.addFlashAttribute("alert_danger", "Games already rated!");
                return "redirect:/";
            }

            if (recommendations.size() > 0 && recommendations.size() != step)
                return "redirect:/game/rate/" + recommendations.size();

            List<GameDTO> games = gameFacade.findAll();
            games.sort(Comparator.comparing(GameDTO::getSteamId));
            model.addAttribute("games",games.subList(step * 10, (step * 10) + 9));
            model.addAttribute("step", step);

            return "game/rate";
        }
        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/rate/{step}/{id}", method = RequestMethod.POST)
    public String rateSingle(
            @PathVariable("step") int step,
            @PathVariable("id") Long id,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes){

        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if (authUser != null){
            RecommendationDTO recommend = new RecommendationDTO();
            UserDTO user = userFacade.findById(authUser.getId());
            recommend.setAuthor(user);
            GameDTO game = gameFacade.findById(id);
            if (game == null)
                throw new ResourceNotFoundException();
            recommend.setGame(game);
            recommend.setVotedUp(true);
            recommend = recommendationFacade.add(recommend);

            game.getRecommendations().add(recommend);
            user.getRecommendations().add(recommend);
            gameFacade.update(game);
            userFacade.update(user);

            if (step == 9){
                redirectAttributes.addFlashAttribute("alert_info", "Games Successfully Rated!");
                return "redirect:/";
            }

            return "redirect:/game/rate/" + (step + 1);
        }
        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public String recommend(HttpServletRequest req,
                            RedirectAttributes redirectAttributes){
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");
        if (authUser == null){
            return loginRedirect(redirectAttributes);
        }
        UserDTO user = userFacade.findById(authUser.getId());
        if (user.getRecommendations().size() < 10){
            redirectAttributes.addFlashAttribute("alert_info", "Please finish rating games");
            return "redirect:/game/rate/" + user.getRecommendations().size();
        }

        return "game/recommend";
    }

    @RequestMapping("/collaborativePearson")
    public String CollaborativePearson(Model model,
                                       HttpServletRequest req,
                                       RedirectAttributes redirectAttributes){
        String redirect = canGetRecommendation(req, redirectAttributes);
        if (redirect != null)
            return redirect;

        //Replace with call to collaborative pearson
        List<GameDTO> games = gameFacade.findAll();
        games.sort(Comparator.comparing(GameDTO::getSteamId));
        model.addAttribute("games",games.subList(0, 4));

        return "game/games";
    }

    @RequestMapping("/collaborativeDice")
    public String CollaborativeDice(Model model,
                                       HttpServletRequest req,
                                       RedirectAttributes redirectAttributes){
        String redirect = canGetRecommendation(req, redirectAttributes);
        if (redirect != null)
            return redirect;

        //replace with call to collaborative dice
        List<GameDTO> games = gameFacade.findAll();
        games.sort(Comparator.comparing(GameDTO::getSteamId));
        model.addAttribute("games",games.subList(0, 4));

        return "game/games";
    }

    @RequestMapping("/descriptionBased")
    public String DescriptionBased(Model model,
                                       HttpServletRequest req,
                                       RedirectAttributes redirectAttributes){
        String redirect = canGetRecommendation(req, redirectAttributes);
        if (redirect != null)
            return redirect;

        //replace with call to content based by description
        List<GameDTO> games = gameFacade.findAll();
        games.sort(Comparator.comparing(GameDTO::getSteamId));
        model.addAttribute("games",games.subList(0, 4));

        return "game/games";
    }

    @RequestMapping("/genreBased")
    public String GenreBased(Model model,
                                       HttpServletRequest req,
                                       RedirectAttributes redirectAttributes){
        String redirect = canGetRecommendation(req, redirectAttributes);
        if (redirect != null)
            return redirect;

        //replace with call to content based by genre
        List<GameDTO> games = gameFacade.findAll();
        games.sort(Comparator.comparing(GameDTO::getSteamId));
        model.addAttribute("games",games.subList(0, 4));

        return "game/games";
    }

    private String loginRedirect(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("alert_info", "Please log in.");
        return "redirect:/auth/login";
    }

    private String canGetRecommendation(HttpServletRequest req,
                                        RedirectAttributes redirectAttributes){
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");
        if (authUser == null){
            return loginRedirect(redirectAttributes);
        }
        UserDTO user = userFacade.findById(authUser.getId());
        if (user.getRecommendations().size() < 10){
            redirectAttributes.addFlashAttribute("alert_info", "Please finish rating games");
            return "redirect:/game/rate/" + user.getRecommendations().size();
        }

        return null;
    }
}
