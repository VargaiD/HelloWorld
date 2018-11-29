package cz.muni.fi.pv254.controllers;

import cz.muni.fi.pv254.parsing.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GameController {

    final static Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private App app;

    @RequestMapping(value = "/game/download", method = RequestMethod.GET)
    public String Index(Model model,
                       HttpServletRequest req,
                       HttpServletResponse res){

        return "/game/download";
    }

    @RequestMapping(value = "/game/download", method = RequestMethod.POST)
    public String Download(@RequestParam String steamId,
                           @RequestParam int minReviews,
                           RedirectAttributes redirectAttributes){

        return "/game/download";
    }
}
