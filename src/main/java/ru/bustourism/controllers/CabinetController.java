package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;

import javax.servlet.http.HttpSession;

@Controller
public class CabinetController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(path = "/cabinet")
    public String cabinet(HttpSession session, ModelMap model) {
        int sessionId = (int) session.getAttribute("userId");

            User byId = usersRepository.findById(sessionId);
            model.addAttribute("user", byId);
            return "cabinet";

    }

}
