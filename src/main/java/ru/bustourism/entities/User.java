package ru.bustourism.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    public User(){}

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "LOGIN", length = 32, unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD", length = 32, nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_TOURS", joinColumns = {@JoinColumn(referencedColumnName = "ID")},
                                    inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID")})
    private List<Tour> tours;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Tour> getTours() {
        return tours;
    }

    public void setTours(List<Tour> tours) {
        this.tours = tours;
    }
}
