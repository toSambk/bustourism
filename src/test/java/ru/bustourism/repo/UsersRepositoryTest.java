package ru.bustourism.repo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.config.TestConfig;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsersRepositoryTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsersRepository usersRepository;

    private User admin;

    private User user;

    @Before
    @Transactional
    public void setup() {
        admin = new User("admin", "admin", true);
        user = new User("user", "123", false);
        manager.persist(admin);
        manager.persist(user);
    }

    @Test
    @Transactional
    public void createUser() {
        User newUser = new User("newUser", "123", false);
        usersRepository.save(newUser);
        try {
            manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", newUser.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void findById() {
        try {
            User found = manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", admin.getId())
                    .getSingleResult();
            assertEquals(found.getId(), admin.getId());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void findByLoginAndPassword() {
        try {
            User found = manager.createQuery("from User where login = :login AND password = :password", User.class)
                    .setParameter("login", admin.getLogin())
                    .setParameter("password", admin.getEncryptedPassword())
                    .getSingleResult();
            assertEquals(found.getId(), admin.getId());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void findByLogin() {
        try {
            User found = manager.createQuery("from User where login = :login", User.class)
                    .setParameter("login", admin.getLogin())
                    .getSingleResult();
            assertEquals(found.getId(), admin.getId());
        } catch (NoResultException e) {
            fail();
        }
    }

    @Test
    @Transactional
    public void deleteAll() {
        usersRepository.deleteAll();
        List<User> found = manager.createQuery("from User", User.class)
                .getResultList();
        assertEquals(0, found.size());
    }

    @Test
    @Transactional
    public void deleteById() {
        usersRepository.deleteById(admin.getId());
        try {
            User found = manager.createQuery("from User where id = :id", User.class)
                    .setParameter("id", admin.getId())
                    .getSingleResult();
            fail();
        } catch (NoResultException e) {
        }
    }

}
