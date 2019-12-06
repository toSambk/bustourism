package ru.bustourism.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.bustourism.config.TestConfig;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
@Ignore// this is no longer needed since we use Spring Security's Login Controller
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mock;

    @Autowired
    private UsersRepository usersRepository;

    @Before
    public void setup() {
        mock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testWrongCredentials() throws Exception {
        mock.perform(
                post("/login")
                        .param("login", "aaaa")
                        .param("password", "bbbb")
        ).andExpect(view().name("mainPage"))
                .andExpect(model().attribute("login", "aaaa"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void testCorrectCredentials() throws Exception {
        User admin = new User("admin", "qwerty", true);
        usersRepository.save(admin);

        MvcResult result = mock.perform(
                post("login")
                        .param("login", admin.getLogin())
                        .param("password", admin.getEncryptedPassword())
        ).andExpect(view().name("redirect:/dashboard"))
                .andReturn();

        assertEquals(admin.getId(), result.getRequest().getSession().getAttribute("userId"));

    }

}
