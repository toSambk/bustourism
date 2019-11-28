package ru.bustourism.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.entities.Tour;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Ignore
public class TourDAOTest {

    @PersistenceContext
    public EntityManager manager;

    @Autowired
    private TourDAO dao;

    @Test
    @Transactional
    public void createTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        dao.createTour(tour);
        Assert.assertNotNull(manager.find(Tour.class, tour.getId()));
    }

    @Test
    @Transactional
    public void deleteTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.persist(tour);
        dao.deleteTour(tour);
        try {
            manager.createQuery("from Tour where id = :id")
                    .setParameter("id", tour.getId()).getSingleResult();
            Assert.assertTrue(false);
        } catch (NoResultException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @Transactional
    public void updateTour() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.persist(tour);
        tour.setName("another");
        dao.updateTour(tour);
        try {
            manager.createQuery("from Tour where name = :name", Tour.class)
                    .setParameter("name", "testname")
                    .getSingleResult();
            Assert.assertTrue(false);
        } catch (NoResultException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    @Transactional
    public void findById() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.persist(tour);
        Tour found = dao.findById(tour.getId());
        Assert.assertEquals(tour.getId(), found.getId());
    }

    @Test
    @Transactional
    public void findByName() {
        Tour tour = new Tour("testname", 50, 10, 4, new Date());
        manager.persist(tour);
        Tour found = dao.findByName("testname");
        Assert.assertEquals(manager.createQuery("from Tour where name = :name", Tour.class)
                .setParameter("name", "testname")
                .getSingleResult().getId(), found.getId());
    }

    @Test
    @Transactional
    public void findToursByRating() {
        Tour goodTour = new Tour("good", 50, 10, 4, new Date());
        Tour badTour = new Tour("bad", 50, 10, 2, new Date());
        manager.persist(goodTour);
        manager.persist(badTour);
        List<Tour> toursAbove4 = dao.findToursByRating(4);
        List<Tour> toursAbove1 = dao.findToursByRating(1);
        Assert.assertEquals(toursAbove4.size(), 1);
        Assert.assertEquals(toursAbove1.size(), 2);
        Assert.assertEquals(toursAbove4.get(0).getId(), goodTour.getId());

    }

    @Test
    @Transactional
    public void findAllTours() {
        Tour goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        Tour mediumTour = new Tour("mediumTour", 100, 70, 3, new Date());
        Tour badTour = new Tour("badTour", 50, 5, 1, new Date());
        manager.persist(goodTour);
        manager.persist(mediumTour);
        manager.persist(badTour);
        List<Tour> users = manager.createQuery("from Tour", Tour.class).getResultList();
        Assert.assertEquals(users.size(), 3);
        Assert.assertEquals(users.stream().filter(x -> x.getId() == badTour.getId()).findFirst().get().getId(), badTour.getId());
        Assert.assertEquals(users.stream().filter(x -> x.getId() == mediumTour.getId()).findFirst().get().getId(), mediumTour.getId());
        Assert.assertEquals(users.stream().filter(x -> x.getId() == goodTour.getId()).findFirst().get().getId(), goodTour.getId());
    }
}
