package ru.bustourism.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private final EntityManager manager;

    public UserDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void createUser(User user) {
        manager.persist(user);
    }

    public void deleteUser(User user) {
        manager.remove(user);
    }

    public void updateUser(User user) {
        manager.merge(user);
    }

    public User findByLogin(String login) {
        return manager.createQuery("from User where login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
    }

    public User findByLoginAndPassword(String login, String password) {
        return manager.createQuery("from User where login = :login AND password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }

    public User findById(int id) {
        return manager.createQuery("from User where id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<User> findAllUsers() {
        return manager.createQuery("from User", User.class).getResultList();
    }

}
