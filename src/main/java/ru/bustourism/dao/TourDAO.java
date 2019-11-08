package ru.bustourism.dao;

import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class TourDAO {

    private final EntityManager manager;

    public TourDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void createTour(Tour tour) {
        manager.persist(tour);
    }

    public void deleteTour(Tour tour) {
        Tour persistTour = manager.createQuery("from Tour where id = :id", Tour.class)
                .setParameter("id", tour.getId())
                .getSingleResult();
        manager.remove(persistTour);
    }

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

    public List<Tour> findToursByRating(int rating) {
        return manager.createQuery("from Tour where rating >= :rating", Tour.class)
                .setParameter("rating", rating)
                .getResultList();
    }

    public List<Tour> findAllTours() {
        return manager.createQuery("from Tour", Tour.class).getResultList();
    }

}
