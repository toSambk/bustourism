package ru.bustourism.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pages/**").denyAll()
                .antMatchers("/", "/login", "/register", "/resources/**").permitAll()
                .antMatchers("/dashboard", "/cabinet", "/tour", "/assessTour")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/cabinet").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/**").hasAnyRole("USER","ADMIN")
                .anyRequest().denyAll();

        http.formLogin()
                .loginPage("/")
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("login")
                .passwordParameter("password")
                .loginProcessingUrl("/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider(PasswordEncoder encoder, UserRolesService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);
        return provider;
    }

}
