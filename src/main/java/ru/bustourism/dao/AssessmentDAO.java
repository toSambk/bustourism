package ru.bustourism.dao;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.Assessment;
import ru.bustourism.entities.Tour;
import ru.bustourism.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

//NOT USED
@Repository
public class AssessmentDAO {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private TourDAO tourDAO;

    @Autowired
    private UserDAO userDAO;

    public Assessment findAssessmentByUserAndTourId(int userId, int tourId) {
        return manager.createQuery("from Assessment where user.id = :userId AND tour.id = :tourId", Assessment.class)
                .setParameter("userId", userId)
                .setParameter("tourId", tourId)
                .getSingleResult();
    }

    public Assessment findAssessmentById(int id) {
        return manager.createQuery("from Assessment where id = :id", Assessment.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public void createAssessment(Assessment assessment) {
        manager.persist(assessment);
    }

    @Transactional
    public void updateAssessment(Assessment assessment) {
            manager.merge(assessment);
    }

    @Transactional
    public void deleteAssessment(Assessment assessment) {
        manager.remove(manager.contains(assessment)?assessment:manager.merge(assessment));
    }

    public List<Assessment> getTourAssessments(int tourId) {
        return manager.createQuery("from Assessment where tour.id = :tourId", Assessment.class )
                .setParameter("tourId", tourId)
                .getResultList();
    }

}
