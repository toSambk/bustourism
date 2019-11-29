package ru.bustourism.dao;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Ignore
public class UserDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UserDAO dao;

    @Test
    @Transactional
    public void createUser() {
        User user = new User("test", "123", false);
        manager.persist(user);
        try {
            manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", user.getId()).getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void deleteUser() {
        User user = new User("test", "123", false);
        manager.persist(user);
        dao.deleteUser(user);
        try {
            manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", user.getId()).getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }

    @Test
    @Transactional
    public void updateUser() {
        User user = new User("test", "123", false);
        manager.persist(user);
        user.setLogin("testNew");
        dao.updateUser(user);
        try {
            manager.createQuery("from User where login = :newLogin", User.class)
                    .setParameter("newLogin", "testNew")
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
        try {
            manager.createQuery("from User where login = :newLogin", User.class)
                    .setParameter("newLogin", "test")
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }

    @Test
    @Transactional
    public void findByLogin() {
        User user = new User("test", "123", false);
        manager.persist(user);
        User found = dao.findByLogin("test");
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());
    }

    @Test
    @Transactional
    public void findByLoginAndPassword() {
        User user = new User("test", "123", false);
        manager.persist(user);
        User found = dao.findByLoginAndPassword("test", "123");
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());
        try {
            dao.findByLoginAndPassword("test", "incorrectPassword");
            Assert.fail("User shouldn't be found.");
        } catch (NoResultException expected) {
        }
    }

    @Test
    @Transactional
    public void findById() {
        User user = new User("test", "123", false);
        manager.persist(user);
        User found = dao.findById(user.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(user.getId(), found.getId());
    }

    @Test
    @Transactional
    public void findAllUsers() {
        User admin = new User("admin", "admin", true);
        User user1 = new User("user1", "123", false);
        User user2 = new User("user2", "456", false);
        manager.persist(admin);
        manager.persist(user1);
        manager.persist(user2);
        List<User> users = dao.findAllUsers();
        Assert.assertEquals(users.size(), 3);
        Assert.assertEquals(users.stream().filter(x -> x.getId() == admin.getId()).findFirst().get().getId(), admin.getId());
        Assert.assertEquals(users.stream().filter(x -> x.getId() == user1.getId()).findFirst().get().getId(), user1.getId());
        Assert.assertEquals(users.stream().filter(x -> x.getId() == user2.getId()).findFirst().get().getId(), user2.getId());
    }


}
