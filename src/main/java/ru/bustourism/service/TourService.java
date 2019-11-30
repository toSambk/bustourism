package ru.bustourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bustourism.dao.AssessmentsRepository;
import ru.bustourism.dao.SeatsRepository;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;
import ru.bustourism.exceptions.NotEnoughSeatsException;
import ru.bustourism.exceptions.SeatNotFoundException;
import ru.bustourism.exceptions.TourNotFoundException;
import ru.bustourism.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class TourService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ToursRepository toursRepository;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    @Autowired
    private SeatsRepository seatsRepository;

    public void assessTourByUser(int userId, int tourId, int assessment) {
        User user = Optional.ofNullable(usersRepository.findById(userId)).orElseThrow(UserNotFoundException::new);
        Tour tour = Optional.ofNullable(toursRepository.findById(tourId)).orElseThrow(TourNotFoundException::new);
        Assessment newAssessment = Optional.ofNullable(assessmentsRepository.findByUserAndTour(user, tour))
                .orElseGet(() -> new Assessment(user, tour, assessment));
        List<Assessment> assessmentsByUser = Optional.ofNullable(user.getAssessments()).orElseGet(ArrayList::new);
        List<Assessment> assessmentsToTour = Optional.ofNullable(tour.getAssessments()).orElseGet(ArrayList::new);

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


    public void buySeatsForTourByUser(int userId, int tourId, int amount) {
        User user = Optional.ofNullable(usersRepository.findById(userId)).orElseThrow(UserNotFoundException::new);
        Tour tour = Optional.ofNullable(toursRepository.findById(tourId)).orElseThrow(TourNotFoundException::new);
        if (amount < 1)
            throw new IllegalArgumentException("Для покупки места необходимо ненулевое положительное значение");
        if (tour.getCurNumberOfSeats() < amount) throw new NotEnoughSeatsException();
        Seat seat = Optional.ofNullable(seatsRepository.findByUserAndTour(user, tour)).orElseGet(() -> new Seat(user, tour, amount));
        List<Seat> seatsOfUser = Optional.ofNullable(user.getSeats()).orElseGet(ArrayList::new);
        List<Seat> seatsOfTour = Optional.ofNullable(tour.getSeats()).orElseGet(ArrayList::new);
        if (seatsOfUser.stream().anyMatch(x -> x.getTour().getId() == tourId)) {
            Seat found = seatsOfUser.stream().filter(x -> x.getTour().getId() == tourId).findFirst().get();
            found.setQuantity(amount);
            seatsOfUser.set(seatsOfUser.indexOf(found), found);
        } else {
            seatsOfUser.add(seat);
        }
        if (seatsOfTour.stream().anyMatch(x -> x.getUser().getId() == userId)) {
            Seat found = seatsOfTour.stream().filter(x -> x.getUser().getId() == userId).findFirst().get();
            found.setQuantity(amount);
            seatsOfTour.set(seatsOfTour.indexOf(found), found);
        } else {
            seatsOfTour.add(seat);
        }
        user.setSeats(seatsOfUser);
        tour.setSeats(seatsOfTour);
        seatsRepository.save(seat);
        usersRepository.save(user);
        toursRepository.save(tour);
    }


    public void declineTourByUser(int userId, int tourId) {
        User user = Optional.ofNullable(usersRepository.findById(userId)).orElseThrow(UserNotFoundException::new);
        Tour tour = Optional.ofNullable(toursRepository.findById(tourId)).orElseThrow(TourNotFoundException::new);
        Seat seat = Optional.ofNullable(seatsRepository.findByUserAndTour(user, tour)).orElseThrow(SeatNotFoundException::new);
        List<Seat> seatsOfUser = Optional.ofNullable(user.getSeats()).orElseThrow(SeatNotFoundException::new);
        List<Seat> seatsOfTour = Optional.ofNullable(tour.getSeats()).orElseThrow(SeatNotFoundException::new);
        if (seatsOfUser.stream().anyMatch(x -> x.getTour().getId() == tourId)) {
            Seat found = seatsOfUser.stream().filter(x -> x.getTour().getId() == tourId).findFirst().get();
            seatsOfUser.remove(found);
        }
        if (seatsOfTour.stream().anyMatch(x -> x.getUser().getId() == userId)) {
            Seat found = seatsOfTour.stream().filter(x -> x.getUser().getId() == userId).findFirst().get();
            seatsOfTour.remove(found);
        }
        user.setSeats(seatsOfUser);
        tour.setSeats(seatsOfTour);
        usersRepository.save(user);
        toursRepository.save(tour);
        seatsRepository.deleteById(seat.getId());

    }

}
