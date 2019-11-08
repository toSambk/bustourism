package ru.entities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
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
        if (manager != null) {
            manager.close();
        }

        if (factory != null) {
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
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Assert.assertNotNull(manager.find(User.class, user.getId()));
        Assert.assertNotNull(manager.find(Tour.class, tour.getId()));

    }

    @Test
    public void testManyToManyLink() {

        TourDAO tourDAO = new TourDAO(manager);
        UserDAO userDAO = new UserDAO(manager);
        User admin = new User("admin", "admin", true);
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "456", false);
        Tour goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        Tour badTour = new Tour("badTour", 50, 5, 1, new Date());
        user1.setTours(Arrays.asList(goodTour, mediumTour));
        user2.setTours(Arrays.asList(goodTour, mediumTour, badTour));
        goodTour.setUsers(Arrays.asList(user1, user2));
        mediumTour.setUsers(Arrays.asList(user1, user2));
        badTour.setUsers(Arrays.asList(user2));

        manager.getTransaction().begin();
        try {
            //userDAO.createUser(admin);
            userDAO.createUser(user1);
            userDAO.createUser(user2);
            tourDAO.createTour(badTour);
            tourDAO.createTour(mediumTour);
            tourDAO.createTour(goodTour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        userDAO.findAllUsers().forEach(x-> {
            System.out.println(x.getId() + "\t" + x.getLogin() + "\t" + x.getPassword());
                    System.out.println("Tours: ");
                    if(x.getTours()!=null){
                        x.getTours().forEach(y-> System.out.println(y.getName() + "\t"));
                    }
        });
        System.out.println("-----------------------");
        tourDAO.findAllTours().forEach(x-> {
            System.out.println(x.getId() + "\t" + x.getName() + "\t" + x.getRating());
            System.out.println("Users: ");
            if(x.getUsers()!=null) {
                x.getUsers().forEach(y -> System.out.println(y.getLogin() + "\t"));
            }
        });



    }

}