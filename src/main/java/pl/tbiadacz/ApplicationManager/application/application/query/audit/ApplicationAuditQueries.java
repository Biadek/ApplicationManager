package pl.tbiadacz.ApplicationManager.application.application.query.audit;

import java.util.List;

public interface ApplicationAuditQueries {

    List<ApplicationAuditListDto> getAllChanges();
}
