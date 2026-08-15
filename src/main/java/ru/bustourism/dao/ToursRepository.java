package ru.bustourism.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Tour;

import java.util.List;

@Repository
public interface ToursRepository extends PagingAndSortingRepository<Tour, Integer> {

    Tour findById(int id);

    List<Tour> findAll();

    Tour findByName(String name);

    Tour save(Tour tour);

    void deleteById(int id);

    void deleteAll();

    @Query("select a from Tour a")
    Page<Tour> findAllInPages(Pageable pageable);

}
