package pl.tbiadacz.ApplicationManager.application.service.query;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.util.List;

public interface ApplicationQueries {

    List<ApplicationListDto> getApplications(int pageNumber, @Nullable String name, @Nullable ApplicationState applicationState);
}
