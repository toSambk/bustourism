package ru.bustourism.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    public User(){}

    public User(String login, String password, boolean administrator){
        this.login = login;
        this.password = password;
        this.administrator = administrator;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "login", length = 32, unique = true, nullable = false)
    private String login;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_tours", joinColumns = {@JoinColumn(referencedColumnName = "ID")},
                                    inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID")})
    private List<Tour> tours;

    @Column(name = "administrator", nullable = false)
    private boolean administrator;










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

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

}
