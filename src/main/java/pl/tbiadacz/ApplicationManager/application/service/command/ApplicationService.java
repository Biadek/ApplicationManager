package pl.tbiadacz.ApplicationManager.application.service.command;

import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.common.IllegalStateChangeException;
import pl.tbiadacz.ApplicationManager.application.model.Application;
import pl.tbiadacz.ApplicationManager.application.model.ApplicationRepository;
import pl.tbiadacz.ApplicationManager.application.model.validation.StateChangeValidator;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StateChangeValidator stateChangeValidator;

    ApplicationService(ApplicationRepository applicationRepository, StateChangeValidator stateChangeValidator) {

        this.applicationRepository = applicationRepository;
        this.stateChangeValidator = stateChangeValidator;
    }

    public void createApplication(ApplicationAttributes applicationAttributes) {

        Application application = new Application(applicationAttributes.getName(), applicationAttributes.getContent());

        applicationRepository.add(application);
    }

    public void updateApplication(ApplicationAttributes applicationAttributes, ApplicationId applicationId) {

        Application application = getApplication(applicationId);

        application.update(applicationAttributes.getName(), applicationAttributes.getContent()).throwIfFailure(IllegalArgumentException::new);

        applicationRepository.update(application);
    }

    public void verifyApplication(ApplicationId applicationId) {

        Application application = getApplication(applicationId);

        application.verify(stateChangeValidator).throwIfFailure(IllegalStateChangeException::new);

        applicationRepository.update(application);
    }

    public void acceptApplication(ApplicationId applicationId) {

        Application application = getApplication(applicationId);

        application.accept(stateChangeValidator).throwIfFailure(IllegalStateChangeException::new);

        applicationRepository.update(application);
    }

    public void rejectApplication(ApplicationId applicationId, String rejectionReason) {

        Application application = getApplication(applicationId);

        application.reject(rejectionReason, stateChangeValidator).throwIfFailure(IllegalStateChangeException::new);

        applicationRepository.update(application);
    }

    public void deleteApplication(ApplicationId applicationId, String deletionReason) {

        Application application = getApplication(applicationId);

        application.delete(deletionReason, stateChangeValidator).throwIfFailure(IllegalStateChangeException::new);

        applicationRepository.update(application);
    }

    public void publishApplication(ApplicationId applicationId) {


        Application application = getApplication(applicationId);

        application.publish(stateChangeValidator).throwIfFailure(IllegalStateChangeException::new);

        applicationRepository.update(application);
    }

    private Application getApplication(ApplicationId applicationId) {
        return applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new IllegalArgumentException("Not found Application with id=" + applicationId));
    }
}
