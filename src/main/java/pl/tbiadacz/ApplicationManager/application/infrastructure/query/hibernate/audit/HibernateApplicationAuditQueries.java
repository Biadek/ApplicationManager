package pl.tbiadacz.ApplicationManager.application.infrastructure.query.hibernate.audit;

import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.ApplicationAuditListDto;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.ApplicationAuditQueries;
import pl.tbiadacz.ApplicationManager.application.domain.Application;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class HibernateApplicationAuditQueries implements ApplicationAuditQueries {

    private final EntityManager entityManager;
    private final ApplicationAuditListDtoFactory factory;

    HibernateApplicationAuditQueries(EntityManager entityManager, ApplicationAuditListDtoFactory factory) {

        this.entityManager = entityManager;
        this.factory = factory;
    }

    @Override
    public List<ApplicationAuditListDto> getAllChanges() {

        AuditQuery query = AuditReaderFactory.get(entityManager)
                .createQuery()
                .forRevisionsOfEntity(Application.class, false, true)
                .addOrder(AuditEntity.revisionNumber().desc());


        ArrayList<Object[]> resultList = (ArrayList) query.getResultList();

        return resultList.stream()
                .map(this::mapToAuditDto)
                .collect(Collectors.toUnmodifiableList());
    }

    private ApplicationAuditListDto mapToAuditDto(Object[] triplet) {

        Application entity = (Application) triplet[0];
        DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) triplet[1];
        RevisionType revisionType = (RevisionType) triplet[2];

        return factory.convert(entity, revisionEntity, revisionType);
    }
}
