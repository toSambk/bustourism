package ru.bustourism.web;

import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;


@WebListener
public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("BusTourismAppPersistenceUnit");
        EntityManager manager = factory.createEntityManager();
        TourDAO tourDAO = new TourDAO(manager);
        UserDAO userDAO = new UserDAO(manager);
        User admin = new User("admin", "admin", true);
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "456", false);
        Tour goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        Tour badTour = new Tour("badTour", 50, 5, 1, new Date());

        user1.setTours(Arrays.asList(goodTour, mediumTour));
        user2.setTours(Arrays.asList(goodTour, mediumTour, badTour));
        goodTour.setUsers(Arrays.asList(user1, user2));
        mediumTour.setUsers(Arrays.asList(user1, user2));
        badTour.setUsers(Arrays.asList(user2));

        manager.getTransaction().begin();
        try {
            userDAO.createUser(user1);
            userDAO.createUser(user2);
            tourDAO.createTour(badTour);
            tourDAO.createTour(mediumTour);
            tourDAO.createTour(goodTour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        } finally {
            manager.close();
        }

        servletContextEvent.getServletContext().setAttribute("factory", factory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        EntityManagerFactory factory = getFactory(servletContextEvent.getServletContext());

        if(factory != null) {
            factory.close();
        }
    }

    public EntityManagerFactory getFactory(ServletContext context) {
        return (EntityManagerFactory) context.getAttribute("factory");
    }
}
