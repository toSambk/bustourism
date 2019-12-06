package ru.bustourism.entities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.dao.AssessmentsRepository;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.service.TourService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EntityCascadeTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

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
        goodTour = new Tour("goodTour", 100, 50, new Date());
        mediumTour = new Tour("mediumTour", 100, 70, new Date());
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(goodTour);
        manager.persist(mediumTour);
        tourService.assessTourByUser(user1.getId(), goodTour.getId(), 5);
        tourService.assessTourByUser(user1.getId(), mediumTour.getId(), 3);
        tourService.assessTourByUser(user2.getId(), goodTour.getId(), 4);
    }

    @Test
    @Transactional
    public void userAndTourAssessmentsGetter() {
        try {
            User foundUser = manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", user1.getId())
                    .getSingleResult();
            assertEquals(2, foundUser.getAssessments().size());
        } catch (NoResultException e) {
            fail();
        }

        try {
            Tour foundTour = manager.createQuery("from Tour where id = :id", Tour.class)
                    .setParameter("id", goodTour.getId())
                    .getSingleResult();
            assertEquals(2, foundTour.getAssessments().size());
        } catch (NoResultException e) {
            fail();
        }

    }


}
