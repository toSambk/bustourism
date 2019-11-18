package ru.bustourism.entities;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TOURS")
public class Tour implements Serializable {

    public Tour(){}

    public Tour(String name, int maxNumberOfSeats, int curNumberOfSeats, int rating, Date date) {
        this.name = name;
        this.maxNumberOfSeats = maxNumberOfSeats;
        this.curNumberOfSeats = curNumberOfSeats;
        this.rating = rating;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "MAX_NUMBER_OF_SEATS", nullable = false)
    @Positive
    private int maxNumberOfSeats;

    @Column(name = "CUR_NUMBER_OF_SEATS")
    @Positive
    private int curNumberOfSeats;

    @Column(name = "RATING", nullable = false, length = 5)
    @Positive
    private int rating;

    @Column(name = "DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany(mappedBy = "tours")
    private List<User> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumberOfSeats() {
        return maxNumberOfSeats;
    }

    public void setMaxNumberOfSeats(int maxNumberOfSeats) {
        this.maxNumberOfSeats = maxNumberOfSeats;
    }

    public int getCurNumberOfSeats() {
        return curNumberOfSeats;
    }

    public void setCurNumberOfSeats(int curNumberOfSeats) {
        this.curNumberOfSeats = curNumberOfSeats;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
