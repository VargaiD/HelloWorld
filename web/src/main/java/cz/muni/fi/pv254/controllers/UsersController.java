package cz.muni.fi.pv254.controllers;

import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.facade.UserFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UsersController {

    final static Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newUser(Model model,
                           RedirectAttributes redAttr,
                           HttpServletRequest req){
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if (authUser != null){
            if(authUser.getIsAdmin()) {
                model.addAttribute("showAdminField", true);
            } else {
                //not admin
                redAttr.addFlashAttribute("alert_danger", "You are signed in.");
                return "redirect:/";
            }
        }
        model.addAttribute("newUser", new UserDTO ());
        model.addAttribute("password", "");

        return "user/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create (
            @Valid @ModelAttribute("newUser") UserDTO formBean,
            @ModelAttribute("password") String password,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req
    ) {
        log.info ("create user ={}", formBean);
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if(authUser.getIsAdmin()) {
                userFacade.add(formBean, password);
                return "redirect:/user/list";
            } else {
                return "redirect:/";
            }
        } else {
            formBean.setIsAdmin(false);
            if (formBean.getEmail() != null){
                UserDTO existingUser = userFacade.findByEmail(formBean.getEmail());
                if (existingUser != null){
                    redirectAttributes.addFlashAttribute("alert_danger", "Email is already in use");
                    return "redirect:/user/create";
                }
                else {
                    userFacade.add(formBean, password);
                }
            }
        }

        redirectAttributes.addFlashAttribute (
                "alert_success",
                "User '" + formBean.getName() + "' was created."
        );
        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/list");

        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if(authUser.getIsAdmin()) {
                model.addAttribute("showCreateNewUser", true);
                model.addAttribute ("allUsers", userFacade.findAll());
                return "user/users";
            } else {
                return redirectToUserDetail(authUser.getId());
            }
        }

        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String viewUser(
            @PathVariable("id") Long id,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/detail/{id}");
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if(authUser.getIsAdmin() || authUser.getId().equals(id)) {
                model.addAttribute ("user", userFacade.findById(id));
            } else {
                model.addAttribute ("user", null);
            }
            return "user/detail";
        }

        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(
            @PathVariable("id") Long id,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/edit/{id}");

        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if(authUser.getIsAdmin() || authUser.getId().equals(id)) {
                model.addAttribute ("editUser", userFacade.findById(id));
                return "user/edit";
            }else {
                model.addAttribute("editUser", null);
            }
            return "user/edit";
        }

        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editUser(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("editUser") UserDTO formBean,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/edit/{id}");

        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if (authUser.getIsAdmin()) {
                userFacade.update(formBean);
                return redirectToUserDetail(formBean.getId());
            } else if (authUser.getId().equals(formBean.getId())) {
                formBean.setIsAdmin(false);
                userFacade.update(formBean);
                return redirectToUserDetail(formBean.getId());
            }else {
                return redirectToUserDetail(authUser.getId());
            }
        }
        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUserGet(
            @PathVariable("id") Long id,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/edit/{id}");
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if (authUser.getIsAdmin()) {
                model.addAttribute("user", userFacade.findById(id));
                return "user/delete";
            } else {
                return redirectToUserDetail(authUser.getId());
            }
        }

        return loginRedirect(redirectAttributes);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String deleteUserPost(
            @PathVariable Long id,
            Model model,
            HttpServletRequest req,
            RedirectAttributes redirectAttributes) {
        log.info ("GET /user/delete/{id}");
        UserDTO authUser = (UserDTO) req.getSession().getAttribute("authUser");

        if(authUser != null) {
            if(authUser.getIsAdmin()) {
                UserDTO userToDelete = userFacade.findById(id);
                if(userToDelete != null) {
                    userFacade.remove(userToDelete);
                    redirectAttributes.addFlashAttribute("alert_info", "User was successfully deleted.");
                    return "redirect:/user/list";
                }
                else {
                    redirectAttributes.addFlashAttribute("alert_danger", "User was not found.");
                    return "redirect:/user/delete/" + id;
                }
            }

            return redirectToUserDetail(authUser.getId());
        }

        return loginRedirect(redirectAttributes);
    }

    private String loginRedirect(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("alert_info", "Please log in.");
        return "redirect:/auth/login";
    }

    private String redirectToUserDetail(Long id) {
        return "redirect:/user/detail/" + id;
    }

}
