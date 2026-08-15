package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CabinetController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(path = "/cabinet")
    public String cabinet(Principal principal, ModelMap model) {
            User found = usersRepository.findByLogin(principal.getName());
            model.addAttribute("user", found);
            return "cabinet";
    }

}
