package pl.tbiadacz.ApplicationManager.application.infrastructure.query.hibernate.audit;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.ApplicationAuditListDto;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.RevisionChangeType;
import pl.tbiadacz.ApplicationManager.application.domain.Application;

import java.sql.Timestamp;

@Service
class ApplicationAuditListDtoFactory {

    ApplicationAuditListDto convert(Application application, DefaultRevisionEntity revisionEntity, RevisionType revisionType) {

        Timestamp changeTimestamp = new Timestamp(revisionEntity.getTimestamp());
        RevisionChangeType type = RevisionChangeType.valueOf(revisionType.toString());

        return new ApplicationAuditListDto(
                application.getId(),
                application.getName(),
                application.getContent(),
                application.getState(),
                application.getRejectionReason(),
                application.getUniqueNumber(),
                changeTimestamp.toLocalDateTime(),
                type
        );
    }
}
