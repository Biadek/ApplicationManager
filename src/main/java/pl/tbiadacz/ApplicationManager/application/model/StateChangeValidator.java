package pl.tbiadacz.ApplicationManager.application.model;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.util.List;

class StateChangeValidator {

    private static final List<StateValidatorStrategy> STRATEGIES = List.of(
        new DeletedStateValidator(),
        new VerifiedStateValidator(),
        new RejectedStateValidator(),
        new AcceptedStateValidator(),
        new PublishedStateValidator()
    );

    static Answer<String> canChangeState(Application application, ApplicationState newState, @Nullable String reason) {

        return STRATEGIES.stream()
                .filter(strategy -> strategy.isApplicable(application.getState()))
                .findFirst()
                .map(strategy -> strategy.stateIsAchievable(application, newState, reason))
                .orElseThrow(UnsupportedClassVersionError::new);
    }
}
