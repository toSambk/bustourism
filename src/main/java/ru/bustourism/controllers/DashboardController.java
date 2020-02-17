package ru.bustourism.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    private final static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping(path = "/dashboard")
    public String dashboard(Principal principal, ModelMap model) {
        try {
            User user = usersRepository.findByLogin(principal.getName());
            List<Tour> tours = toursRepository.findAll();
            model.addAttribute("tours", tours);
            model.addAttribute("user", user);
            return "dashboard";
        } catch(Exception notFound) {
            logger.warn("Пользователь не найден - либо пароль не верен");
            return "mainPage";
        }
    }

}
