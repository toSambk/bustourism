package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.User;

import javax.servlet.http.HttpSession;

@Controller
public class CabinetController {

    @Autowired
    private UserDAO userDAO;

    @GetMapping(path = "/cabinet")
    public String cabinet(HttpSession session, ModelMap model) {
        int sessionId = (int) session.getAttribute("userId");

            User byId = userDAO.findById(sessionId);
            model.addAttribute("user", byId);
            return "cabinet";

    }

}
