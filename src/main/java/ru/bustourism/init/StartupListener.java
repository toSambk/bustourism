package ru.bustourism.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import java.util.Arrays;
import java.util.Date;

@Component
public class StartupListener {

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private UserDAO userDAO;

    @EventListener
    @Transactional
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvent) {
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
        userDAO.createUser(admin);
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        tourDAO.createTour(badTour);
        tourDAO.createTour(mediumTour);
        tourDAO.createTour(goodTour);
    }

}
