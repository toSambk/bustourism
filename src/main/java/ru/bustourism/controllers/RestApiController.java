package ru.bustourism.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bustourism.dao.AssessmentsRepository;
import ru.bustourism.dao.SeatsRepository;
import ru.bustourism.dao.ToursRepository;
import ru.bustourism.dao.UsersRepository;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Seat;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

@RestController
public class RestApiController {

    @Autowired
    private ToursRepository toursRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AssessmentsRepository assessmentsRepository;

    @Autowired
    private SeatsRepository seatsRepository;

    @GetMapping("api/tour/find")
    public Tour findTour(@RequestParam("tourId") int tourId) {
        return toursRepository.findById(tourId);
    }

    @GetMapping("api/user/find")
    public User findUser(@RequestParam("userId") int userId) {
        return usersRepository.findById(userId);
    }

    @GetMapping("api/assessment/find")
    public Assessment findAssessmentByUserAndTourId(@RequestParam("userId") int userId, @RequestParam("tourId") int tourId) {
        return assessmentsRepository.findByUserAndTour(usersRepository.findById(userId), toursRepository.findById(tourId));
    }

    @GetMapping("api/seat/find")
    public Seat findSeatByUserAndTourId(@RequestParam("userId") int userId, @RequestParam("tourId") int tourId) {
        return seatsRepository.findByUserAndTour(usersRepository.findById(userId), toursRepository.findById(tourId));
    }

    @GetMapping("api/tours/find")
    public Page<Tour> findTours(@RequestParam("tourPage") int tourPage) {
        return toursRepository.findAllInPages(PageRequest.of(tourPage - 1, 25, Sort.by(Sort.Order.asc("id"))));
    }


}
