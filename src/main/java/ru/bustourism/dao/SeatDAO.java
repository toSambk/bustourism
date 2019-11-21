package ru.bustourism.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.Seat;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SeatDAO {

    @PersistenceContext
    private EntityManager manager;

    public Seat findSeatByUserAndTourId(int userId, int tourId) {
        return manager.createQuery("from Seat where userId = :userId AND tourId = :tourId", Seat.class)
                .setParameter("userId", userId)
                .setParameter("tourId", tourId)
                .getSingleResult();
    }

    public Seat findSeatById(int id) {
        return manager.createQuery("from Seat where id = :id", Seat.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void createSeat(Seat seat) {
        manager.persist(seat);
    }

    @Transactional
    public void updateSeat(Seat seat) {
        manager.merge(seat);
    }

    @Transactional
    public void deleteSeat(Seat seat) {
        manager.remove(manager.contains(seat)?seat:manager.merge(seat));
    }


}
