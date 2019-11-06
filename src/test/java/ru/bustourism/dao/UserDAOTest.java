package ru.bustourism.dao;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UserDAOTest {

    private EntityManagerFactory factory;

    private EntityManager manager;

    private UserDAO dao;

    @Before
    public void setup() {
        factory = Persistence.createEntityManagerFactory("BusTourismAppPersistenceUnit");
        manager = factory.createEntityManager();
        dao = new UserDAO(manager);
    }

    @After
    public void cleanup() {
        if (manager!=null) {
            manager.close();
        }

        if(factory!=null){
            factory.close();
        }
    }

    @Test
    public void createUser() {

        User user = new User("test", "123");
        manager.getTransaction().begin();
        try {
            dao.createUser(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
        Assert.assertNotNull(manager.find(User.class, user.getId()));
    }

    @Test
    public void deleteUser() {
        User user = new User("test", "123");
        manager.getTransaction().begin();
        try {
            dao.createUser(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
        }

        manager.getTransaction().begin();
        try {
            User persistentUser = manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", user.getId())
                    .getSingleResult();
            dao.deleteUser(persistentUser);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            Assert.assertTrue(false);
            throw e;
        }

    }

    @Test
    public void updateUser() {

        User user = new User("test", "123");
        manager.getTransaction().begin();
        try {
            dao.createUser(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
        }

        manager.getTransaction().begin();
        try {
            User persistentUser = manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", user.getId())
                    .getSingleResult();
            persistentUser.setLogin("testNew");
            dao.updateUser(persistentUser);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            Assert.assertTrue(false);
            throw e;
        }

    }

    @Test
    public void findByLogin() {
    }

    @Test
    public void findByLoginAndPassword() {
    }
}
