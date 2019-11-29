package ru.bustourism.entities;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tours")
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
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "max_number_of_seats", nullable = false)
    @Positive
    private int maxNumberOfSeats;

    @Column(name = "cur_number_of_seats")
    @Positive
    private int curNumberOfSeats;

    @Column(name = "rating", nullable = false, length = 5)
    @Positive
    private double rating;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    private List<Seat> seats;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    private List<Assessment> assessments;









    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
