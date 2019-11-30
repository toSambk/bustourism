package ru.bustourism.controllers;

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
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    @GetMapping(path = "/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {

        try {
            int accountId = (int) session.getAttribute("userId");
            User user = usersRepository.findById(accountId);
            List<Tour> tours = toursRepository.findAll();
            model.addAttribute("tours", tours);
            model.addAttribute("user", user);
            return "dashboard";
        } catch(NoResultException notFound) {
            return "mainPage";
        }
    }

}
