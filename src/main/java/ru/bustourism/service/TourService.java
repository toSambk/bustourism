package ru.bustourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bustourism.dao.*;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.exceptions.NotEnoughSeatsException;
import ru.bustourism.exceptions.TourNotFoundException;
import ru.bustourism.exceptions.UserNotFoundException;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class TourService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    public void assessTourByUser(int userId, int tourId, int assessment) {
        User user = usersRepository.findById(userId);
        Tour tour = toursRepository.findById(tourId);
        Assessment newAssessment = assessmentsRepository.findByUserAndTour(user, tour);
        List<Assessment> assessmentsByUser;
        List<Assessment> assessmentsToTour;
        if (newAssessment == null) {
            newAssessment = new Assessment(user, tour, assessment);
        }
        if (user == null) throw new UserNotFoundException();
        if (tour == null) throw new TourNotFoundException();
        if (user.getAssessments() != null) {
            assessmentsByUser = user.getAssessments();
        } else {
            assessmentsByUser = new ArrayList<>();
        }
        if (tour.getAssessments() != null) {
            assessmentsToTour = tour.getAssessments();
        } else {
            assessmentsToTour = new ArrayList<>();
        }
        if (assessmentsByUser.stream().anyMatch(x -> x.getTour().getId() == tourId)) {
            Assessment found = assessmentsByUser.stream().filter(x -> x.getTour().getId() == tourId).findFirst().get();
            found.setValue(assessment);
            assessmentsByUser.set(assessmentsByUser.indexOf(found), found);
        } else {
            assessmentsByUser.add(newAssessment);
        }
        if (assessmentsToTour.stream().anyMatch(x -> x.getUser().getId() == userId)) {
            Assessment found = assessmentsToTour.stream().filter(x -> x.getUser().getId() == userId).findFirst().get();
            found.setValue(assessment);
            assessmentsToTour.set(assessmentsToTour.indexOf(found), found);
        } else {
            assessmentsToTour.add(newAssessment);
        }
        user.setAssessments(assessmentsByUser);
        tour.setAssessments(assessmentsToTour);
        assessmentsRepository.save(newAssessment);
        usersRepository.save(user);
        toursRepository.save(tour);
    }


    public double getTourRating(int tourId) {
        OptionalDouble average = toursRepository.findById(tourId).getAssessments().stream()
                .mapToDouble(Assessment::getValue).average();
        return average.isPresent() ? average.getAsDouble() : 0;
    }


//    public void buySeats(int userId, int tourId, int amount) {
//        Tour tour = tourDAO.findById(tourId);
//        if (amount > tour.getCurNumberOfSeats() || amount < 1) throw new NotEnoughSeatsException();
//        try {
//            Seat found = seatDAO.findSeatByUserAndTourId(userId, tourId);
//            found.setQuantity(found.getQuantity() + amount);
//            seatDAO.updateSeat(found);
//        } catch (NoResultException e) {
//            seatDAO.createSeat(new Seat(userDAO.findById(userId), tourDAO.findById(tourId), amount));
//        }
//        int curNumberOfSeats = tour.getCurNumberOfSeats();
//        tour.setCurNumberOfSeats(curNumberOfSeats - amount);
//        tourDAO.updateTour(tour);
//    }

}
