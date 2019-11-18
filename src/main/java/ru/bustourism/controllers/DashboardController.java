package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private UserDAO userDAO;

    @GetMapping(path = "/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {

        try {
            int accountId = (int) session.getAttribute("userId");
            User user = userDAO.findById(accountId);
            List<Tour> tours = tourDAO.findAllTours();
            model.addAttribute("tours", tours);
            model.addAttribute("user", user);
            return "/dashboard.jsp";
        } catch(NoResultException notFound) {
            return "mainPage";
        }
    }

}
