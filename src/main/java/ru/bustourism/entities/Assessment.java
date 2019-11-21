package ru.bustourism.entities;

import javax.persistence.*;

@Entity
@Table(name = "assessment")
public class Assessment {

    public Assessment(){}

    public Assessment(int userId, int tourId, int value) {
        this.userId = userId;
        this.tourId = tourId;
        this.value = value;
    }

    @Column(name = "user_id")
    private int userId;

    @Column(name = "tour_id")
    private int tourId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "value")
    private int value;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
