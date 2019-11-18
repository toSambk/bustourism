package ru.bustourism.config;

import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.bustourism.init.StartupListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Configuration
@ComponentScan(basePackages = "ru.bustourism", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AppConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = StartupListener.class)
})
public class TestConfig {

    @Bean
    public EntityManagerFactory factory() {
        return Persistence.createEntityManagerFactory("TestPersistenceUnit");
    }

    @Bean
    public EntityManager manager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

}
