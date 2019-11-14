package ru.bustourism.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = "ru.bustourism")
public class AppConfig {

    @Bean
    public EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("BusTourismAppPersistenceUnit");
    }

    @Bean
    public EntityManager getEntityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

}
