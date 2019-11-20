package ru.bustourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bustourism.dao.AssessmentDAO;
import ru.bustourism.dao.TourDAO;
import ru.bustourism.dao.UserDAO;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentDAO assessmentDAO;

    public void assessTourByUser(int userId, int tourId, int assessment) {
        try {
            Assessment found = assessmentDAO.findAssessmentByUserAndTourId(userId, tourId);
            found.setValue(assessment);
            assessmentDAO.updateAssessment(found);
        } catch (NoResultException e) {
            assessmentDAO.createAssessment(new Assessment(userId, tourId, assessment));
        }
    }

    public double getTourRating(int tourId) {
        OptionalDouble average = assessmentDAO.getTourAssessments(tourId).stream()
                .mapToDouble(x -> x.getValue()).average();
        return  average.isPresent()? average.getAsDouble() : 0;
    }



}
