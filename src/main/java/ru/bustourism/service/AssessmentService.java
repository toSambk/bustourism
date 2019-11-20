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

}
