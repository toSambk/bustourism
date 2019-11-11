package ru.bustourism.web;

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
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntityManagerFactory factory = StartupListener.getFactory(req.getServletContext());
        EntityManager manager = factory.createEntityManager();

        TourDAO tourDAO = new TourDAO(manager);

        try {
            int userId = (int) req.getSession().getAttribute("userId");
            List<Tour> tours = tourDAO.findAllTours();
            req.setAttribute("tours", tours);
            req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
        } catch(NoResultException notFound) {
            req.getRequestDispatcher("/").forward(req, resp);
        } finally {
            manager.close();
        }



    }

}
