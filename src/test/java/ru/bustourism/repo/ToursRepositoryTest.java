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
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.entities.Tour;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ToursRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ToursRepository toursRepository;

    private Tour tour1;

    private Tour tour2;

    @Before
    @Transactional
    public void setup() {
        tour1 = new Tour("goodTour", 100, 50, 5, new Date());
        tour2 = new Tour("badTour", 100, 40, 2, new Date());
        manager.persist(tour1);
        manager.persist(tour2);
    }

    @Test
    @Transactional
    public void createUser() {
        Tour newTour = new Tour("newTour", 100, 50, 5, new Date());
        toursRepository.save(newTour);
        try {
            manager.createQuery("from Tour where id = :id", Tour.class)
                    .setParameter("id", newTour.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void findById() {
        try {
            Tour found = manager.createQuery("from Tour where id = :id", Tour.class)
                    .setParameter("id", tour1.getId())
                    .getSingleResult();
            assertEquals(found.getId(), tour1.getId());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void findByName() {
        try {
            Tour found = manager.createQuery("from Tour where name = :name", Tour.class)
                    .setParameter("name", tour1.getName())
                    .getSingleResult();
            assertEquals(found.getId(), tour1.getId());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void deleteAll() {
        toursRepository.deleteAll();
        List<Tour> found = manager.createQuery("from Tour", Tour.class)
                .getResultList();
        assertEquals(0, found.size());
    }

    @Test
    @Transactional
    public void deleteById() {
        toursRepository.deleteById(tour1.getId());
        try {
            Tour found = manager.createQuery("from Tour where id = :id", Tour.class)
                    .setParameter("id", tour1.getId())
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }

}
