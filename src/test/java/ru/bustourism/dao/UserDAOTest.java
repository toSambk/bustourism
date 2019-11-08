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
import javax.persistence.NoResultException;
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

        User user = new User("test", "123", false);
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
        User user = new User("test", "123", false);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
        }

        manager.getTransaction().begin();
        try {
            dao.deleteUser(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            Assert.assertTrue(false);
            throw e;
        }

    }

    @Test
    public void updateUser() {

        User user = new User("test", "123", false);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
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

        User found = manager.createQuery("from User where login = :newLogin", User.class)
                .setParameter("newLogin", "testNew")
                .getSingleResult();
        Assert.assertNotNull(found);
        try {
            manager.createQuery("from User where login = :newLogin", User.class)
                    .setParameter("newLogin", "test")
                    .getSingleResult();
            Assert.assertTrue(false);
        } catch(NoResultException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void findByLogin() {

        User user = new User("test", "123", false);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        User found = dao.findByLogin("test");
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());

    }

    @Test
    public void findByLoginAndPassword() {

        User user = new User("test", "123", false);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        User found = dao.findByLoginAndPassword("test", "123");
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());

        try {
            dao.findByLoginAndPassword("test", "incorrectPassword");
            Assert.fail("User shouldn't be found.");
        } catch(NoResultException expected) {
        }

    }

    @Test
    public void findById() {

        User user = new User("test", "123", false);
        manager.getTransaction().begin();
        try {
            manager.persist(user);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        User found = dao.findById(user.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());

    }
}
