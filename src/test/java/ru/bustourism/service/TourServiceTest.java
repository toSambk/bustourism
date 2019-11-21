package ru.bustourism.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.bustourism.config.TestConfig;
import ru.bustourism.dao.AssessmentDAO;
import ru.bustourism.dao.SeatDAO;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.exceptions.NotEnoughSeatsException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TourServiceTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private AssessmentDAO assessmentDAO;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private SeatDAO seatDAO;

    private User user1, user2, user3;

    private Tour goodTour, mediumTour;

    @Before
    public void setup() {
        user1 = new User("user1", "123", false);
        user2 = new User("user2", "123", false);
        user3 = new User("user3", "123", false);
        goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(user1);
            manager.persist(goodTour);
            manager.persist(mediumTour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }

    @Test
    public void assessTourByUserNew() {
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
        try {
            Assessment found = assessmentDAO.findAssessmentByUserAndTourId(user1.getId(), goodTour.getId());
            assertNotNull(found);
            assertEquals(5, found.getValue());
        } catch(NoResultException e) {
            fail();
        }
    }

    @Test
    public void assessTourByUserChange() {
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 3);
        try {
            Assessment found = assessmentDAO.findAssessmentByUserAndTourId(user1.getId(), goodTour.getId());
            assertNotNull(found);
            assertEquals(3, found.getValue());
        } catch(NoResultException e) {
            fail();
        }
    }

    @Test
    public void getTourRating() {
        Assessment assessment1 = new Assessment(user1.getId(), goodTour.getId(), 4);
        Assessment assessment2 = new Assessment(user2.getId(), goodTour.getId(), 3);
        Assessment assessment3 = new Assessment(user3.getId(), goodTour.getId(), 1);
        Assessment assessment4 = new Assessment(user1.getId(), mediumTour.getId(), 2);
        assessmentDAO.createAssessment(assessment1);
        assessmentDAO.createAssessment(assessment2);
        assessmentDAO.createAssessment(assessment3);
        assessmentDAO.createAssessment(assessment4);
        double tourRating = tourService.getTourRating(goodTour.getId());
        assertEquals(2.6, tourRating, 0.1);
        assertNotEquals(3, tourRating, 0.1);
        assertNotEquals(2, tourRating, 0.1);
    }

    @Test
    public void addTourToUser() {
        tourService.addTourToUser(user1.getId(), goodTour.getId());
        List<Tour> tours = userDAO.findById(user1.getId()).getTours();
        List<User> users = tourDAO.findById(goodTour.getId()).getUsers();
        assertEquals(1, tours.size());
        assertEquals(1, users.size());

        if(!tours.stream().anyMatch(x-> x.getId() == goodTour.getId()) && !users.stream().anyMatch(x-> x.getId() == user1.getId())) {
            fail();
        }
    }

    @Test
    public void buyTour() {
        tourService.buySeats(user1.getId(), goodTour.getId(), 10);
        Seat seat = seatDAO.findSeatByUserAndTourId(user1.getId(), goodTour.getId());
        assertNotNull(seat);
        assertEquals(10, seat.getQuantity());
        assertEquals(user1.getId(), seat.getUserId());
        assertEquals(goodTour.getId(), seat.getTourId());
        Tour newTour = tourDAO.findById(goodTour.getId());
        assertEquals(40, newTour.getCurNumberOfSeats());
        try {
            tourService.buySeats(user1.getId(), mediumTour.getId(), 80);
            fail();
        } catch (NotEnoughSeatsException e) {}
    }

}
