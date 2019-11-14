package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.User;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping(path = "/login")
    public String login(HttpSession session, @RequestParam String login, @RequestParam String password, ModelMap model) {

        try {
            User found = userDAO.findByLoginAndPassword(login, password);
            session.setAttribute("userId", found.getId());
            return "redirect:/dashboard";
        } catch(NoResultException notFound) {
            model.addAttribute("login", login);
            return "mainPage";
        }

    }
}
