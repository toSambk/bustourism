package ru.bustourism.dao;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bustourism.entities.Tour;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TourDAOTest {

    private EntityManagerFactory factory;

    private EntityManager manager;

    private TourDAO dao;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("BusTourismAppPersistenceUnit");
        manager = factory.createEntityManager();
        dao = new TourDAO(manager);
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
    public void createTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.getTransaction().begin();
        try {
            dao.createTour(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Assert.assertNotNull(manager.find(Tour.class, tour.getId()));
    }

    @Test
    public void deleteTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        manager.getTransaction().begin();
        try{
            dao.deleteTour(tour);
            manager.getTransaction().commit();
        } catch (Exception expected) {
            manager.getTransaction().rollback();
            Assert.assertTrue(false);
            throw expected;
        }

    }

    @Test
    public void updateTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        manager.getTransaction().begin();
        Tour persistentTour;
        try {
            persistentTour = manager.createQuery("from Tour where id = :id", Tour.class)
                    .setParameter("id", tour.getId())
                    .getSingleResult();
            persistentTour.setName("another");
            dao.updateTour(persistentTour);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            Assert.assertTrue(false);
            throw e;
        }
        Tour found = manager.find(Tour.class, persistentTour.getId());
        Assert.assertNotNull(found);
        try {
            manager.createQuery("from Tour where name = :name", Tour.class)
                    .setParameter("name", "testname")
                    .getSingleResult();
            Assert.assertTrue(false);
        } catch(NoResultException e) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void findById() {

        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Tour found = dao.findById(tour.getId());
        Assert.assertEquals(tour.getId(), found.getId());

    }

    @Test
    public void findByName() {

        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(tour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        Tour found = dao.findByName("testname");

        Assert.assertEquals(manager.createQuery("from Tour where name = :name", Tour.class)
                .setParameter("name", "testname")
                .getSingleResult().getId(), found.getId());

    }

    @Test
    public void findToursByRating() {

        Tour goodTour = new Tour("good", 50, 10, 4, new Date());
        Tour badTour = new Tour("bad", 50, 10, 2, new Date());
        manager.getTransaction().begin();
        try {
            manager.persist(goodTour);
            manager.persist(badTour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        List<Tour> toursAbove4 = dao.findToursByRating(4);
        List<Tour> toursAbove1 = dao.findToursByRating(1);
        Assert.assertEquals(toursAbove4.size(), 1);
        Assert.assertEquals(toursAbove1.size(), 2);
        Assert.assertEquals(toursAbove4.get(0).getId(), goodTour.getId());

    }

    @Test
    public void findAllTours() {

        Tour goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        Tour badTour = new Tour("badTour", 50, 5, 1, new Date());

        manager.getTransaction().begin();
        try {
            manager.persist(goodTour);
            manager.persist(mediumTour);
            manager.persist(badTour);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        List<Tour> users = manager.createQuery("from Tour", Tour.class).getResultList();

        Assert.assertEquals(users.size(), 3);
        Assert.assertEquals(users.stream().filter(x-> x.getId() == badTour.getId()).findFirst().get().getId(), badTour.getId());
        Assert.assertEquals(users.stream().filter(x-> x.getId() == mediumTour.getId()).findFirst().get().getId(), mediumTour.getId());
        Assert.assertEquals(users.stream().filter(x-> x.getId() == goodTour.getId()).findFirst().get().getId(), goodTour.getId());

    }
}
