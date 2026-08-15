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
import ru.bustourism.dao.AssessmentsRepository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AssessmentsRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    private Assessment assessment;

    private User user;

    private Tour tour;

    @Before
    @Transactional
    public void setup() {
        user = new User("user", "123", false);
        tour = new Tour("tour", 100, 50, new Date());
        assessment = new Assessment(user, tour, 3);
        manager.persist(user);
        manager.persist(tour);
    }

    @Test
    @Transactional
    public void save() {
        assessmentsRepository.save(assessment);
        try {
            manager.createQuery("from Assessment where id = :id", Assessment.class)
                    .setParameter("id", assessment.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
    }


    @Test
    @Transactional
    public void findById() {
        manager.persist(assessment);
        Assessment found = assessmentsRepository.findById(assessment.getId());
        assertEquals(found.getId(), assessment.getId());
        assertEquals(found.getUser().getId(), assessment.getUser().getId());
    }

    @Test
    @Transactional
    public void findByUserAndTour() {
        manager.persist(assessment);
        Assessment found = assessmentsRepository.findByUserAndTour(user, tour);
        assertEquals(found.getId(), assessment.getId());
        assertEquals(found.getUser().getId(), assessment.getUser().getId());
    }


}
