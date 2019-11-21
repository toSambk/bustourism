package ru.bustourism.entities;

import javax.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    public Seat(){}

    public Seat(int userId, int tourId, int quantity) {
        this.userId = userId;
        this.tourId = tourId;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "tour_id")
    private int tourId;

    @Column(name = "quantity")
    private int quantity;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

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
}
