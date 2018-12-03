package cz.muni.fi.pv254.controllers;


import cz.muni.fi.pv254.dto.UserAuthenticateDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.facade.UserFacade;
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
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    final static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.info("GET request: /auth/login");
        if (req.getSession().getAttribute("authUser") != null) {
            return "redirect:/";
        }
        return "auth/authentication";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            HttpServletResponse res) {
        log.info("POST request: /auth/login");
        UserDTO userDTO = userFacade.authenticate(new UserAuthenticateDTO(email, password));

        if (userDTO == null) {
            log.warn("POST request: /auth/login; wrong login information, entered mail={}", email);
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Wrong mail or password of user");
            return "redirect:/auth/login";
        }

        userDTO.setIsAdmin(userFacade.isAdmin(userDTO));

        HttpSession session = req.getSession(true);
        session.setAttribute("authUser", userDTO);
        log.info("POST request: /auth/login; user with id {} has been logged in", userDTO.getId());
        redirectAttributes.addFlashAttribute("alert_info",
                "User with name " + userDTO.getName() + " has been logged in.");
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        log.info("GET request: /logout");
        HttpSession session = req.getSession(true);
        session.removeAttribute("authUser");
        redirectAttributes.addFlashAttribute("alert_info", "You have been successfully logged out.");
        return "redirect:/auth/login";
    }
}
