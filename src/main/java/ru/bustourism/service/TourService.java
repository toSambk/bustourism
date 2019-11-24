package ru.bustourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bustourism.dao.AssessmentDAO;
import ru.bustourism.dao.SeatDAO;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.exceptions.NotEnoughSeatsException;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class TourService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private AssessmentDAO assessmentDAO;

    @Autowired
    private SeatDAO seatDAO;

    public void addTourToUser(int userId, int tourId) {
        User user = userDAO.findById(userId);
        Tour tour = tourDAO.findById(tourId);
        List<Tour> tours = user.getTours();
        List<User> users = tour.getUsers();
        tours.add(tour);
        users.add(user);
        user.setTours(tours);
        tour.setUsers(users);
        userDAO.updateUser(user);
        tourDAO.updateTour(tour);
    }

    public void assessTourByUser(int userId, int tourId, int assessment) {
        try {
            Assessment found = assessmentDAO.findAssessmentByUserAndTourId(userId, tourId);
            found.setValue(assessment);
            assessmentDAO.updateAssessment(found);
        } catch (NoResultException e) {
            assessmentDAO.createAssessment(new Assessment(userId, tourId, assessment));
        }
        Tour found = tourDAO.findById(tourId);
        found.setRating(getTourRating(tourId));
        tourDAO.updateTour(found);
    }

    public double getTourRating(int tourId) {
        OptionalDouble average = assessmentDAO.getTourAssessments(tourId).stream()
                .mapToDouble(x -> x.getValue()).average();
        return  average.isPresent()? average.getAsDouble() : 0;
    }

    public void buySeats(int userId, int tourId, int amount) {
        Tour tour = tourDAO.findById(tourId);
        if (amount > tour.getCurNumberOfSeats() || amount < 1) throw new NotEnoughSeatsException();
        try {
            Seat found = seatDAO.findSeatByUserAndTourId(userId, tourId);
            found.setQuantity(found.getQuantity() + amount);
            seatDAO.updateSeat(found);
        } catch (NoResultException e) {
            seatDAO.createSeat(new Seat(userId, tourId, amount));
        }
        int curNumberOfSeats = tour.getCurNumberOfSeats();
        tour.setCurNumberOfSeats(curNumberOfSeats - amount);
        tourDAO.updateTour(tour);
    }

}
