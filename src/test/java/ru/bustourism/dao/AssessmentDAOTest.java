package ru.bustourism.dao;

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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Ignore
public class AssessmentDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private AssessmentDAO assessmentDAO;

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
        user1.setTours(Arrays.asList(goodTour, mediumTour));
        goodTour.setUsers(Arrays.asList(user1));
        mediumTour.setUsers(Arrays.asList(user1));
        manager.persist(user1);
        manager.persist(goodTour);
        manager.persist(mediumTour);
    }


    @Test
    @Transactional
    public void createAssessment() {
        Assessment assessment = new Assessment(user1, goodTour, 3);
        assessmentDAO.createAssessment(assessment);
        Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                .setParameter("id", assessment.getId())
                .getSingleResult();
        assertNotNull(found);
        assertEquals(found.getId(), assessment.getId());
    }

    @Test
    @Transactional
    public void updateAssessment() {
        Assessment assessment = new Assessment(user1, goodTour, 3);
        manager.persist(assessment);
        assessment.setValue(5);
        assessmentDAO.updateAssessment(assessment);
        Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                .setParameter("id", assessment.getId())
                .getSingleResult();
        assertNotNull(found);
        assertEquals(found.getId(), assessment.getId());
        assertEquals(found.getValue(), 5);
        assertNotEquals(found.getValue(), 3);
    }

    @Test
    @Transactional
    public void findAssessmentByUserAndTourId() {
        Assessment assessment1 = new Assessment(user1, goodTour, 3);
        Assessment assessment2 = new Assessment(user1, mediumTour, 2);
        manager.persist(assessment1);
        manager.persist(assessment2);
        Assessment found1 = assessmentDAO.findAssessmentByUserAndTourId(assessment1.getUser().getId(), assessment1.getTour().getId());
        Assessment found2 = assessmentDAO.findAssessmentByUserAndTourId(assessment2.getUser().getId(), assessment2.getTour().getId());
        assertNotNull(found1);
        assertNotNull(found2);
        assertEquals(assessment1.getValue(), found1.getValue());
        assertEquals(assessment2.getValue(), found2.getValue());
    }

    @Test
    @Transactional
    public void findAssessmentById() {
        Assessment assessment = new Assessment(user1, goodTour, 3);
        manager.persist(assessment);
        Assessment found = assessmentDAO.findAssessmentById(assessment.getId());
        assertNotNull(found);
        assertEquals(assessment.getId(), found.getId());
    }

    @Test
    @Transactional
    public void deleteAssessment() {
        Assessment assessment = new Assessment(user1, goodTour, 3);
        manager.persist(assessment);
        assessmentDAO.deleteAssessment(assessment);
        try {
            Assessment found = manager.createQuery("from Assessment where id = :id", Assessment.class)
                    .setParameter("id", assessment.getId())
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }

    @Test
    @Transactional
    public void getTourAssessments() {
        Assessment assessment1 = new Assessment(user1, goodTour, 4);
        Assessment assessment2 = new Assessment(user2, goodTour, 4);
        Assessment assessment3 = new Assessment(user3, goodTour, 4);
        Assessment assessment4 = new Assessment(user1, mediumTour, 2);
        List<Assessment> expected = Arrays.asList(assessment1, assessment2, assessment3);
        assessmentDAO.createAssessment(assessment1);
        assessmentDAO.createAssessment(assessment2);
        assessmentDAO.createAssessment(assessment3);
        assessmentDAO.createAssessment(assessment4);
        List<Assessment> tourAssessments = assessmentDAO.getTourAssessments(goodTour.getId());
        assertEquals(3, tourAssessments.size());
        tourAssessments.forEach(x -> {
            if (x.getId() != expected.stream().filter(y -> y.getId() == x.getId()).findFirst().get().getId()) fail();
        });
    }

}
