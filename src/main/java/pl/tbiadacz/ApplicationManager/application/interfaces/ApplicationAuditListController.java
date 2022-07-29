package pl.tbiadacz.ApplicationManager.application.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.ApplicationAuditListDto;
import pl.tbiadacz.ApplicationManager.application.application.query.audit.ApplicationAuditQueries;

import java.util.List;

@RestController
@RequestMapping("/application/audit")
class ApplicationAuditListController {


    private final ApplicationAuditQueries applicationAuditQueries;

    ApplicationAuditListController(ApplicationAuditQueries applicationAuditQueries) {
        this.applicationAuditQueries = applicationAuditQueries;
    }

    @GetMapping
    private ResponseEntity<List<ApplicationAuditListDto>> getApplicationsHistory() {

        return ResponseEntity.ok(applicationAuditQueries.getAllChanges());
    }
}
