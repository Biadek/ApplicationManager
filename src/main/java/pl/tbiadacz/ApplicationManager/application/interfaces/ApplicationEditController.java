package pl.tbiadacz.ApplicationManager.application.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.service.command.ApplicationService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/application")
class ApplicationEditController {

    private final ApplicationService applicationService;

    ApplicationEditController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createApplication(@RequestBody ApplicationForm applicationForm) {

       applicationService.createApplication(applicationForm);

        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Void> createApplication(@PathVariable("id") Long applicationId, @RequestBody ApplicationForm applicationForm) {

        applicationService.updateApplication(applicationForm, ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<Void> verifyApplication(@PathVariable("id") Long applicationId) {

        applicationService.verifyApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<Void> acceptApplication(@PathVariable("id") Long applicationId) {

        applicationService.acceptApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<Void> publishApplication(@PathVariable("id") Long applicationId) {

        applicationService.publishApplication(ApplicationId.of(applicationId));

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> rejectApplication(@PathVariable("id") Long applicationId, @RequestBody String rejectionReason) {

        applicationService.rejectApplication(ApplicationId.of(applicationId), rejectionReason);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long applicationId, @RequestBody String deletionReason) {

        applicationService.deleteApplication(ApplicationId.of(applicationId), deletionReason);

        return ResponseEntity.ok().build();
    }
}
