package ru.bustourism.controllers;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.bustourism.config.TestConfig;
import ru.bustourism.config.WebConfig;
import ru.bustourism.config.security.SecurityConfig;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.forms.AcceptingTourBean;

import javax.servlet.Filter;
import java.util.Date;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, SecurityConfig.class, WebConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class TourControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter securityFilter;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    private MockMvc mockMvc;

    private Tour goodTour;

    private User admin;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(securityFilter)
                .apply(springSecurity())
                .build();

        goodTour = new Tour("goodTour", 100, 50, new Date());
        admin = new User("admin", "admin", true);
        goodTour.setRating(0);
        usersRepository.save(admin);
        toursRepository.save(goodTour);
    }


    @Test
    public void unauthorizedShouldNotBeAllowedToVisitTour() throws Exception {
        // unauthorized user should be redirected to /
        mockMvc.perform(MockMvcRequestBuilders.get("/tour")
                .param("tourId", "" + goodTour.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void authorizedUserShouldSeeTour() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tour")
                .param("tourId", "" + goodTour.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tour"))
                .andReturn();
    }

    @Test
    @Ignore
    public void unauthorizedUserCouldNotBuyATour() throws Exception {
        AcceptingTourBean acceptingTourBean = new AcceptingTourBean();
        acceptingTourBean.setQuantity(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/tour")
                                        .param("tourId", "" + goodTour.getId())
                                        .requestAttr("acceptForm", acceptingTourBean)
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Ignore
    public void authorizedUserCouldNotBuyATour() throws Exception {
        AcceptingTourBean acceptingTourBean = new AcceptingTourBean();
        acceptingTourBean.setQuantity(2);
        mockMvc.perform(MockMvcRequestBuilders.post("/tour")
                .param("tourId", "" + goodTour.getId())
                .requestAttr("acceptForm", acceptingTourBean)
        ).andExpect(status().isOk())
                .andExpect(model().attributeExists("tour"))
                .andReturn();
    }



}
