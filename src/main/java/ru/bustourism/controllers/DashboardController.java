package ru.bustourism.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.entities.Tour;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TourDAO tourDAO;

    @GetMapping(path = "/dashboard")
    public String dashboard(HttpSession session, ModelMap model) {

        try {
            int accountId = (int) session.getAttribute("userId");
            List<Tour> tours = tourDAO.findAllTours();
            model.addAttribute("tours", tours);
            return "dashboard";
        } catch(NoResultException notFound) {
            return "mainPage";
        }
    }

}
