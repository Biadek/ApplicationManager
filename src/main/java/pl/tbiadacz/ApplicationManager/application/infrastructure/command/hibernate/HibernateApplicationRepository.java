package pl.tbiadacz.ApplicationManager.application.infrastructure.command.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.model.Application;
import pl.tbiadacz.ApplicationManager.application.model.ApplicationRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Optional;

import static pl.tbiadacz.ApplicationManager.application.model.Application.D_ID;


@Repository
class HibernateApplicationRepository implements ApplicationRepository {

    private final SessionFactory sessionFactory;

    HibernateApplicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void add(Application application) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            session.save(application);
            transaction.commit();
        }
    }

    @Override
    @Transactional
    public void update(Application application) {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();
            session.update(application);
            transaction.commit();
        }
    }

    @Override
    @Transactional
    public Optional<Application> findById(ApplicationId id) {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Application> criteria = criteriaBuilder.createQuery(Application.class);
            Root<Application> root = criteria.from(Application.class);

            criteria.select(root)
                    .where(criteriaBuilder.equal(root.get(D_ID), id.getId()));

            return session.createQuery(criteria).uniqueResultOptional();
        }
    }
}
