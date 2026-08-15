package ru.bustourism.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.User;
import java.util.List;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<User, Integer> {

    User findById(int id);

    User findByLoginAndEncryptedPassword(String login, String encryptedPassword);

    User findByLogin(String login);

    List<User> findAll();

    void deleteAllById(int id);

    void deleteAll();

    User save(User user);

    @Query("select a from User a")
    Page<Seat> findAllInPages(Pageable pageable);

}
