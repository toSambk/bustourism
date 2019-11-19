package ru.bustourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import java.util.List;

@Service
public class TourService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TourDAO tourDAO;

    public void addTourToUser(int userId, int tourId) {
        User user = userDAO.findById(userId);
        List<Tour> tours = user.getTours();
        tours.add(tourDAO.findById(tourId));
        user.setTours(tours);
        userDAO.updateUser(user);
    }
}
