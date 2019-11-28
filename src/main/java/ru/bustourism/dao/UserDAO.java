package ru.bustourism.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//NOT USED
@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager manager;

    private UserDAO(){}

    public UserDAO(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public void createUser(User user) {
        manager.persist(user);
    }

    @Transactional
    public void deleteUser(User user) {
        manager.remove(manager.contains(user)?user:manager.merge(user));
    }

    @Transactional
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
