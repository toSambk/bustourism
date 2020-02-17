package ru.bustourism.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.service.TourService;

import java.util.Date;

@Component
public class StartupListener {

    private final static Logger logger = LoggerFactory.getLogger(StartupListener.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxStartEvent) {
        User admin = new User("admin", encoder.encode("admin"), true);
        User user1 = new User("user1", encoder.encode("123"), false);
        User user2 = new User("user2", encoder.encode("456"), false);
        Tour goodTour = new Tour("goodTour", 100, 50, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, new Date());
        Tour badTour = new Tour("badTour", 50, 5, new Date());
        goodTour.setRating(0);
        mediumTour.setRating(0);
        badTour.setRating(0);
        usersRepository.save(admin);
        usersRepository.save(user1);
        usersRepository.save(user2);
        toursRepository.save(badTour);
        toursRepository.save(mediumTour);
        toursRepository.save(goodTour);
        toursRepository.save(badTour);
        toursRepository.save(mediumTour);
        toursRepository.save(goodTour);
    }

}
