package cz.muni.fi.pv254.controllers;

import cz.muni.fi.pv254.facade.UserFacade;
import cz.muni.fi.pv254.parsing.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    final static Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private App app;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Home(Model model,
                       HttpServletRequest req,
                       HttpServletResponse res){

        return "/home";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String Download(){
        app.setDebug(1);
        app.setOffsetDiff(100);
//        app.addGameId(892760L);
//        app.addGameId(911520L);
//        app.addGameId(964030L);
//        app.inteligentParseAllGanes();
        app.downloadTop100();
        return "/home";
    }
}
