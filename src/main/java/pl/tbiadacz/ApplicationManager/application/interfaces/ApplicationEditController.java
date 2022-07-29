package pl.tbiadacz.ApplicationManager.application.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tbiadacz.ApplicationManager.application.application.command.ApplicationService;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/application")
class ApplicationEditController {

    private static final String ID_PARAM = "id";

    private final ApplicationService applicationService;

    ApplicationEditController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    private ResponseEntity<Void> createApplication(@RequestBody ApplicationForm applicationForm) {

        applicationService.createApplication(applicationForm);

        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{id}/update")
    private ResponseEntity<Void> createApplication(@PathVariable(ID_PARAM) Long applicationId, @RequestBody ApplicationForm applicationForm) {

        applicationService.updateApplication(applicationForm, ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/verify")
    private ResponseEntity<Void> verifyApplication(@PathVariable(ID_PARAM) Long applicationId) {

        applicationService.verifyApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/accept")
    private ResponseEntity<Void> acceptApplication(@PathVariable(ID_PARAM) Long applicationId) {

        applicationService.acceptApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publish")
    private ResponseEntity<Void> publishApplication(@PathVariable(ID_PARAM) Long applicationId) {

        applicationService.publishApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reject")
    private ResponseEntity<Void> rejectApplication(@PathVariable(ID_PARAM) Long applicationId, @RequestBody String rejectionReason) {

        applicationService.rejectApplication(ApplicationId.of(applicationId), rejectionReason);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    private ResponseEntity<Void> deleteApplication(@PathVariable(ID_PARAM) Long applicationId, @RequestBody String deletionReason) {

        applicationService.deleteApplication(ApplicationId.of(applicationId), deletionReason);

        return ResponseEntity.ok().build();
    }
}
