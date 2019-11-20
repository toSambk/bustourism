package ru.bustourism.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.bustourism.config.TestConfig;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AssessmentDAOTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private AssessmentDAO assessmentDAO;

    private User user1;

    private Tour goodTour, mediumTour;

    @Before
    public void setup() {
        user1 = new User("user1", "123", false);
        goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        user1.setTours(Arrays.asList(goodTour, mediumTour));
        goodTour.setUsers(Arrays.asList(user1));
        mediumTour.setUsers(Arrays.asList(user1));
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
    public void createAssessment() {

        Assessment assessment = new Assessment(user1.getId(), goodTour.getId(), 3);
        manager.getTransaction().begin();
        try {
            assessmentDAO.createAssessment(assessment);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                .setParameter("id", assessment.getId())
                .getSingleResult();

        assertNotNull(found);
        assertEquals(found.getId(), assessment.getId());

    }

    @Test
    public void updateAssessment() {

        Assessment assessment = new Assessment(user1.getId(), goodTour.getId(), 3);
        manager.getTransaction().begin();
        try {
            manager.persist(assessment);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        assessment.setValue(5);

        manager.getTransaction().begin();
        try {
            assessmentDAO.updateAssessment(assessment);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                .setParameter("id", assessment.getId())
                .getSingleResult();

        assertNotNull(found);
        assertEquals(found.getId(), assessment.getId());
        assertEquals(found.getValue(), 5);
        assertNotEquals(found.getValue(), 3);

    }

    @Test
    public void findAssessmentByUserAndTourId() {
        Assessment assessment1 = new Assessment(user1.getId(), goodTour.getId(), 3);
        Assessment assessment2 = new Assessment(user1.getId(), mediumTour.getId(), 2);

        manager.getTransaction().begin();
        try {
            manager.persist(assessment1);
            manager.persist(assessment2);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        Assessment found1 = assessmentDAO.findAssessmentByUserAndTourId(assessment1.getUserId(), assessment1.getTourId());
        Assessment found2 = assessmentDAO.findAssessmentByUserAndTourId(assessment2.getUserId(), assessment2.getTourId());
        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(assessment1.getValue(), found1.getValue());
        assertEquals(assessment2.getValue(), found2.getValue());
    }

    @Test
    public void findAssessmentById() {
        Assessment assessment = new Assessment(user1.getId(), goodTour.getId(), 3);
        manager.getTransaction().begin();
        try {
            manager.persist(assessment);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        Assessment found = assessmentDAO.findAssessmentById(assessment.getId());
        assertNotNull(found);
        assertEquals(assessment.getId(), found.getId());
    }

    @Test
    public void deleteAssessment() {

        Assessment assessment = new Assessment(user1.getId(), goodTour.getId(), 3);
        manager.getTransaction().begin();
        try {
            manager.persist(assessment);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        manager.getTransaction().begin();
        try {
            assessmentDAO.deleteAssessment(assessment);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            fail();
            throw e;
        }

        try {
            Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                    .setParameter("id", assessment.getId())
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }

    }
}
