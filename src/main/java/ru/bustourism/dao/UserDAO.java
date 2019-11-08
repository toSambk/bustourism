package ru.bustourism.dao;

import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserDAO {

    private EntityManager manager;

    UserDAO(EntityManager manager) {
        this.manager = manager;
    }

    public void createUser(User user) {
        manager.persist(user);
    }

    public void deleteUser(User user) {

        User persistUser = manager.createQuery("from User where id = :id", User.class)
                .setParameter("id", user.getId())
                .getSingleResult();
        manager.remove(persistUser);
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




}
