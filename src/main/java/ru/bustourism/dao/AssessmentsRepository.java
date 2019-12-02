package ru.bustourism.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

@Repository
public interface AssessmentsRepository extends PagingAndSortingRepository<Assessment, Integer> {

    Assessment findById(int id);

    Assessment findByUserAndTour(User user, Tour tour);

    Assessment save(Assessment assessment);

    void deleteById(int id);

    @Query("select a from Assessment a")
    Page<Assessment> findAllInPages(Pageable pageable);
}
