package pl.tbiadacz.ApplicationManager.application.infrastructure.query.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.tbiadacz.ApplicationManager.application.application.query.ApplicationListDto;
import pl.tbiadacz.ApplicationManager.application.application.query.ApplicationQueries;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;
import pl.tbiadacz.ApplicationManager.application.domain.Application;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static pl.tbiadacz.ApplicationManager.application.domain.Application.*;


@Service
class HibernateApplicationQueries implements ApplicationQueries {

    private static final int PAGE_SIZE = 10;

    private final SessionFactory sessionFactory;
    private final EntityManager entityManager;

    HibernateApplicationQueries(SessionFactory sessionFactory, EntityManager entityManager) {

        this.sessionFactory = sessionFactory;
        this.entityManager = entityManager;
    }

    @Override
    public List<ApplicationListDto> getApplications(int pageNumber, @Nullable String name, @Nullable ApplicationState applicationState) {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationListDto> criteria = criteriaBuilder.createQuery(ApplicationListDto.class);
            Root<Application> root = criteria.from(Application.class);

            CriteriaQuery<ApplicationListDto> select = criteria
                    .multiselect(
                            root.get(D_ID),
                            root.get(D_NAME),
                            root.get(D_CONTENT),
                            root.get(D_STATE),
                            root.get(D_REJECTION_REASON),
                            root.get(D_UNIQUE_NUMBER)
                    );

            addPredicates(name, applicationState, criteriaBuilder, root, select);

            TypedQuery<ApplicationListDto> typedQuery = entityManager.createQuery(select);
            typedQuery.setFirstResult(getFirstResultOnPage(pageNumber));
            typedQuery.setMaxResults(PAGE_SIZE);

            return typedQuery.getResultList();
        }
    }

    private void addPredicates(String name, ApplicationState applicationState, CriteriaBuilder criteriaBuilder, Root<Application> root, CriteriaQuery<ApplicationListDto> select) {

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(name)) {
            predicates.add(criteriaBuilder.equal(root.get(D_NAME), name));
        }
        if (applicationState != null) {
            predicates.add(criteriaBuilder.equal(root.get(D_STATE), applicationState));
        }

        select.where(predicates.toArray(new Predicate[0]));
    }

    private Integer getTotalCountOfApplications() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Application.class)));

        return entityManager.createQuery(countQuery)
                .getSingleResult()
                .intValue();
    }

    private int getLastPageNumber() {

        BigDecimal totalCountOfApplications = BigDecimal.valueOf(getTotalCountOfApplications());

        return totalCountOfApplications.divide(BigDecimal.valueOf(PAGE_SIZE), RoundingMode.CEILING).intValue();
    }

    private int getFirstResultOnPage(int pageNumber) {

        if (pageNumber <= 1) {
            return 0;
        }

        int updatedPageNumber = Math.min(pageNumber, getLastPageNumber());
        int previousPageNumber = updatedPageNumber - 1;
        int lastElementOnPreviousPage = previousPageNumber * PAGE_SIZE;

        return lastElementOnPreviousPage + 1;
    }
}
