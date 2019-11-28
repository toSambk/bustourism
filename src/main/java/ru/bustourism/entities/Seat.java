package ru.bustourism.entities;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    public Seat(){}

//    public Seat(int userId, int tourId, int quantity) {
//        this.userId = userId;
//        this.tourId = tourId;
//        this.quantity = quantity;
//    }

    public Seat(User user, Tour tour, int quantity) {
        this.user = user;
        this.tour = tour;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "quantity")
    private int quantity;






    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
