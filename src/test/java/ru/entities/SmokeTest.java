package ru.entities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.Date;

public class SmokeTest {

    private EntityManagerFactory factory;

    private EntityManager manager;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("BusTourismAppPersistenceUnit");
        manager = factory.createEntityManager();
    }

    @After
    public void cleanup() {
        if (manager!=null) {
            manager.close();
        }

        if(factory!=null){
            factory.close();
        }
    }

    @Test
    public void createUser() {

        User user = new User("test", "123", false);
        Tour tour = new Tour("testtour", 100, 5, 3, new Date());

        user.setTours(Arrays.asList(tour));
        tour.setUsers(Arrays.asList(user));

        manager.getTransaction().begin();
        try {
            manager.persist(user);
            manager.persist(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertNotNull(manager.find(User.class, user.getId()));
        Assert.assertNotNull(manager.find(Tour.class, tour.getId()));

//        manager.find(User.class, user.getId()).getTours().forEach(x-> System.out.println(x.getName()));
    }

}
