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
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Ignore
public class SeatDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SeatDAO seatDAO;

    private User user1;

    private Tour goodTour;

    private Seat seat;

    @Before
    @Transactional
    public void setup() {
        user1 = new User("user1", "123", false);
        goodTour = new Tour("goodTour", 100, 50, 5, new Date());
        seat = new Seat(user1, goodTour, 5);
        manager.persist(user1);
        manager.persist(goodTour);
    }

    @Test
    @Transactional
    public void findSeatByUserAndTourId() {
        Seat newseat = new Seat(user1, goodTour, 5);
        manager.persist(newseat);
        Seat seatByUserAndTourId = seatDAO.findSeatByUserAndTourId(user1.getId(), goodTour.getId());
        assertNotNull(seatByUserAndTourId);
        assertEquals(5, seatByUserAndTourId.getQuantity());
    }

    @Test
    @Transactional
    public void findSeatById() {
        manager.persist(seat);
        Seat seatById = seatDAO.findSeatById(seat.getId());
        assertNotNull(seatById);
        assertEquals(seat.getId(), seatById.getId());
    }

    @Test
    @Transactional
    public void createSeat() {
        seatDAO.createSeat(seat);
        try {
            manager.createQuery("from Seat where id = :id", Seat.class)
                    .setParameter("id", seat.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void updateSeat() {
        manager.persist(seat);
        seat.setQuantity(10);
        seatDAO.updateSeat(seat);
        try {
            Seat changedSeat = manager.createQuery("from Seat where id = :id", Seat.class)
                    .setParameter("id", seat.getId())
                    .getSingleResult();
            assertNotNull(changedSeat);
            assertEquals(seat.getId(), changedSeat.getId());
            assertEquals(10, changedSeat.getQuantity());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void deleteSeat() {
        manager.persist(seat);
        seatDAO.deleteSeat(seat);
        try {
            manager.createQuery("from Seat where id = :id", Seat.class)
                    .setParameter("id", seat.getId())
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }
}
