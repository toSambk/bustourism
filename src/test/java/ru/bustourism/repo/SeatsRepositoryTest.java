package ru.bustourism.repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.dao.SeatsRepository;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SeatsRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SeatsRepository seatsRepository;

    private Seat seat;

    private User user;

    private Tour tour;

    @Before
    @Transactional
    public void setup() {
        seat = new Seat(3);
        user = new User("user", "123", false);
        tour = new Tour("tour", 100, 50, 4, new Date());
        user.setSeats(Collections.singletonList(seat));
        tour.setSeats(Collections.singletonList(seat));
        manager.persist(user);
        manager.persist(tour);
    }


    @Test
    @Transactional
    public void findById() {
        Seat found = seatsRepository.findById(seat.getId());
        assertEquals(found.getId(), seat.getId());
    }

}
