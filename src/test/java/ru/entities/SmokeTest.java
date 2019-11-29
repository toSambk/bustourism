package ru.entities;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

@Ignore
public class SmokeTest {

    private EntityManagerFactory factory;

    private EntityManager manager;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnitPostgres");
        manager = factory.createEntityManager();
    }

    @After
    public void closeup() {
        if (manager != null) manager.close();
        if (factory != null) factory.close();
    }

    @Test
    public void testCascade() {
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "123", false);
        User user3 = new User("user3", "123", false);
        Tour goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        Assessment assessment = new Assessment(user1, goodTour, 5);
//        List<Assessment> list = Arrays.asList(assessment);
//        user1.setAssessments(list);
//        goodTour.setAssessments(list);
        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(goodTour);
        manager.persist(assessment);
        manager.getTransaction().commit();

//        Assert.assertNotNull(goodTour.getAssessments());
//        Assessment id = manager.createQuery("from Assessment where user.id = :id", Assessment.class)
//                .setParameter("id", user1.getId()).getSingleResult();
//
//        id.getUser().getPassword();
    }

}