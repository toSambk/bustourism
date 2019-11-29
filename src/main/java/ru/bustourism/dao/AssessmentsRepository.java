package ru.bustourism.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

@Repository
public interface AssessmentsRepository extends CrudRepository<Assessment, Integer> {

    Assessment findById(int id);

    Assessment findByUserAndTour(User user, Tour tour);

    Assessment save(Assessment assessment);
}
