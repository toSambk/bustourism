package ru.bustourism.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourServiceTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private TourService tourService;

    private User user1, user2, user3;

    private Tour goodTour, mediumTour;

    @Before
    @Transactional
    public void setup() {
        user1 = new User("user1", "123", false);
        user2 = new User("user2", "123", false);
        user3 = new User("user3", "123", false);
        goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        manager.persist(user1);
        manager.persist(goodTour);
        manager.persist(mediumTour);
        manager.persist(user2);
        manager.persist(user3);
    }

    @Test
    @Transactional
    public void assessTourByUserNew() {

        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
        List<Assessment> found = manager.createQuery("from Assessment where user.id = :userId AND tour.id = :tourId", Assessment.class)
                .setParameter("userId", user1.getId())
                .setParameter("tourId", goodTour.getId())
                .getResultList();

        assertEquals(1, found.size());
        assertEquals(user1.getId(), found.get(0).getUser().getId());
        assertEquals(goodTour.getId(), found.get(0).getTour().getId());

        tourService.assessTourByUser(user1.getId(), mediumTour.getId(), 3);
        assertEquals(2, manager.createQuery("from Assessment", Assessment.class).getResultList().size());
        try {
            manager.createQuery("from Assessment where user.id = :userId AND tour.id = :tourId", Assessment.class)
                    .setParameter("userId", user1.getId())
                    .setParameter("tourId", mediumTour.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }

    }

    @Test
    @Transactional
    public void assessTourByUserChange() {
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
        List<Assessment> found = manager.createQuery("from Assessment where user.id = :userId AND tour.id = :tourId", Assessment.class)
                .setParameter("userId", user1.getId())
                .setParameter("tourId", goodTour.getId())
                .getResultList();
        assertEquals(1, found.size());
        assertEquals(user1.getId(), found.get(0).getUser().getId());
        assertEquals(goodTour.getId(), found.get(0).getTour().getId());
        assertEquals(5, found.get(0).getValue());

        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 3);

        List<Assessment> foundChanged = manager.createQuery("from Assessment where user.id = :userId AND tour.id = :tourId", Assessment.class)
                .setParameter("userId", user1.getId())
                .setParameter("tourId", goodTour.getId())
                .getResultList();

        assertEquals(1, foundChanged.size());
        assertEquals(user1.getId(), foundChanged.get(0).getUser().getId());
        assertEquals(goodTour.getId(), foundChanged.get(0).getTour().getId());
        assertEquals(3, foundChanged.get(0).getValue());

    }

    @Test
    @Transactional
    public void getTourRating() {
        Assessment assessment1 = new Assessment(user1, goodTour, 4);
        Assessment assessment2 = new Assessment(user2, goodTour, 3);
        Assessment assessment3 = new Assessment(user3, goodTour, 1);
        Assessment assessment4 = new Assessment(user1, mediumTour, 2);
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 4);
        tourService.assessTourByUser(user2.getId(), goodTour.getId(), 3);
        tourService.assessTourByUser(user3.getId(), goodTour.getId(), 1);
        tourService.assessTourByUser(user1.getId(), mediumTour.getId(), 2);
        double tourRating = tourService.getTourRating(goodTour.getId());
        assertEquals(2.6, tourRating, 0.1);
        assertNotEquals(3, tourRating, 0.1);
        assertNotEquals(2, tourRating, 0.1);
    }

//    @Test
//    @Transactional
//    public void buyTour() {
//        tourService.buySeats(user1.getId(), goodTour.getId(), 10);
//        Seat seat = seatDAO.findSeatByUserAndTourId(user1.getId(), goodTour.getId());
//        assertNotNull(seat);
//        assertEquals(10, seat.getQuantity());
//        assertEquals(user1.getId(), seat.getUser().getId());
//        assertEquals(goodTour.getId(), seat.getTour().getId());
//        Tour newTour = tourDAO.findById(goodTour.getId());
//        assertEquals(40, newTour.getCurNumberOfSeats());
//        try {
//            tourService.buySeats(user1.getId(), mediumTour.getId(), 80);
//            fail();
//        } catch (NotEnoughSeatsException e) {
//        }
//    }

}
