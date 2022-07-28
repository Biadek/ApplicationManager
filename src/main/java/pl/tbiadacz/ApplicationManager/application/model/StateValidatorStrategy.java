package pl.tbiadacz.ApplicationManager.application.model;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

interface StateValidatorStrategy {

    boolean isApplicable(ApplicationState newState);

    Answer<String> stateIsAchievable(Application application, ApplicationState newState, @Nullable String reason);
}
