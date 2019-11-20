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
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AssessmentServiceTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private AssessmentDAO assessmentDAO;

    @Autowired
    private AssessmentService assessmentService;

    private User user1, user2;

    private Tour goodTour, mediumTour;

    @Before
    public void setup() {
        user1 = new User("user1", "123", false);
        user2 = new User("user2", "123", false);
        goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        user1.setTours(Arrays.asList(goodTour, mediumTour));
        goodTour.setUsers(Arrays.asList(user1));
        mediumTour.setUsers(Arrays.asList(user1, user2));
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
        assessmentService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
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

        assessmentService.assessTourByUser(user1.getId(), goodTour.getId(), 5);

        assessmentService.assessTourByUser(user1.getId(), goodTour.getId(), 3);

        try {
            Assessment found = assessmentDAO.findAssessmentByUserAndTourId(user1.getId(), goodTour.getId());
            assertNotNull(found);
            assertEquals(3, found.getValue());
        } catch(NoResultException e) {
            fail();
        }


    }

}
