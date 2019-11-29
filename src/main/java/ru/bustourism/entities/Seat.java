package ru.bustourism.entities;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    public Seat(){}

    public Seat(User user, Tour tour, int quantity) {
        this.user = user;
        this.tour = tour;
        this.quantity = quantity;
    }

    public Seat(int quantity) {
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne (cascade = CascadeType.REFRESH)
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
