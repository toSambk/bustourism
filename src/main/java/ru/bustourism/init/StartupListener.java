package ru.bustourism.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.dao.*;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import java.util.Arrays;
import java.util.Date;

@Component
public class StartupListener {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    @EventListener
    @Transactional
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvent) {
        User admin = new User("admin", "admin", true);
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "456", false);
        Tour goodTour = new Tour("goodTour", 100, 50, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, new Date());
        Tour badTour = new Tour("badTour", 50, 5, new Date());
        usersRepository.save(admin);
        usersRepository.save(user1);
        usersRepository.save(user2);
        toursRepository.save(badTour);
        toursRepository.save(mediumTour);
        toursRepository.save(goodTour);
    }

}
