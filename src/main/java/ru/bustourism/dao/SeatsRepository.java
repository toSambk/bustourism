package ru.bustourism.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

@Repository
public interface SeatsRepository extends CrudRepository<Seat, Integer> {

    Seat findById(int id);

    Seat findByUserAndTour(User user, Tour tour);

    Seat save(Seat seat);

    void deleteById(int id);

}
