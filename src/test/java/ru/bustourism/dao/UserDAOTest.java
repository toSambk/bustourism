package ru.bustourism.dao;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.bustourism.config.AppConfig;
import ru.bustourism.entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOTest {

    @Autowired
    private EntityManager manager;

    @Autowired
    private UserDAO dao;

    @Before
    public void setup() {

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

    @Test
    public void findAllUsers() {

        User admin = new User("admin", "admin", true);
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "456", false);

        manager.getTransaction().begin();
        try {
            manager.persist(admin);
            manager.persist(user1);
            manager.persist(user2);
            manager.getTransaction().commit();
        } catch(Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }

        List<User> users = manager.createQuery("from User", User.class).getResultList();

        Assert.assertEquals(users.size(), 3);
        Assert.assertEquals(users.stream().filter(x-> x.getId() == admin.getId()).findFirst().get().getId(), admin.getId());
        Assert.assertEquals(users.stream().filter(x-> x.getId() == user1.getId()).findFirst().get().getId(), user1.getId());
        Assert.assertEquals(users.stream().filter(x-> x.getId() == user2.getId()).findFirst().get().getId(), user2.getId());
    }


}
