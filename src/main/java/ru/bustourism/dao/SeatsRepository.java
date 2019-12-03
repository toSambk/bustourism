package ru.bustourism.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

@Repository
public interface SeatsRepository extends PagingAndSortingRepository<Seat, Integer> {

    Seat findById(int id);

    Seat findByUserAndTour(User user, Tour tour);

    Seat save(Seat seat);

    void deleteById(int id);

    @Query("select a from Seat a")
    Page<Seat> findAllInPages(Pageable pageable);

    @Query("select a from Seat a where a.user.id = :userId")
    Page<Seat> findSeatsByUserId(@Param("userId") int userId, Pageable pageable);

}
