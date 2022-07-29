package pl.tbiadacz.ApplicationManager.application.interfaces;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tbiadacz.ApplicationManager.application.application.query.ApplicationListDto;
import pl.tbiadacz.ApplicationManager.application.application.query.ApplicationQueries;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.util.List;

@RestController
@RequestMapping("/application-list")
class ApplicationListController {

    private final ApplicationQueries applicationQueries;

    ApplicationListController(ApplicationQueries applicationQueries) {
        this.applicationQueries = applicationQueries;
    }

    @GetMapping
    private ResponseEntity<List<ApplicationListDto>> getApplications(
            @Parameter(description = "Current page number. (Numbering starts at 1)") @RequestParam("page") int pageNumber,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "state", required = false) ApplicationState applicationState) {

        return ResponseEntity.ok(applicationQueries.getApplications(pageNumber, name, applicationState));
    }
}
