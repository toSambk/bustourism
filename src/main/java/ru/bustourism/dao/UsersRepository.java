package ru.bustourism.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.User;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {

    User findById(int id);

    User findByLoginAndPassword(String login, String password);

    User findByLogin(String login);

    List<User> findAll();

    void deleteAllById(int id);

    void deleteAll();

    User save(User user);

}
