package ru.bustourism.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.Tour;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//NOT USED
@Repository
public class TourDAO {

    @PersistenceContext
    private EntityManager manager;

    private TourDAO(){}

    public TourDAO(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void createTour(Tour tour) {
        manager.persist(tour);
    }

    @Transactional
    public void deleteTour(Tour tour) {
        manager.remove(manager.contains(tour)?tour:manager.merge(tour));
    }

    @Transactional
    public void updateTour(Tour tour) {
        manager.merge(tour);
    }

    public Tour findById(int id) {
        return manager.createQuery("from Tour where id = :id", Tour.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Tour findByName(String name) {
        return manager.createQuery("from Tour where name = :name", Tour.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Tour> findToursByRating(double rating) {
        return manager.createQuery("from Tour where rating >= :rating", Tour.class)
                .setParameter("rating", rating)
                .getResultList();
    }

    public List<Tour> findAllTours() {
        return manager.createQuery("from Tour", Tour.class).getResultList();
    }

}
