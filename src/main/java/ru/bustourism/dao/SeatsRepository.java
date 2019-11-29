package ru.bustourism.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Seat;

@Repository
public interface SeatsRepository extends CrudRepository<Seat, Integer> {

    Seat findById(int id);

}
