package ru.bustourism.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.User;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRolesService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User found = usersRepository.findByLogin(username);
        if (found == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }

        List<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (found.getLogin().equals("admin")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new org.springframework.security.core.userdetails.User(username, found.getEncryptedPassword(), roles);

    }
}
