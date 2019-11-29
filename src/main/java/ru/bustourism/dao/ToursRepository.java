package ru.bustourism.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Tour;

import java.util.List;

@Repository
public interface ToursRepository extends CrudRepository<Tour, Integer> {

    Tour findById(int id);

    List<Tour> findAll();

    Tour findByName(String name);

    Tour save(Tour tour);

    void deleteById(int id);

    void deleteAll();

}
