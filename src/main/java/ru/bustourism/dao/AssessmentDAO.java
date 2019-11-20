package ru.bustourism.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bustourism.entities.Assessment;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AssessmentDAO {

    @PersistenceContext
    private EntityManager manager;

    public Assessment findAssessmentByUserAndTourId(int userId, int tourId) {
        return manager.createQuery("from Assessment where userId = :userId AND tourId = :tourId", Assessment.class)
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
        return manager.createQuery("from Assessment where tourId = :tourId", Assessment.class )
                .setParameter("tourId", tourId)
                .getResultList();
    }

}
