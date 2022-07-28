package pl.tbiadacz.ApplicationManager.application.model.validation;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

interface StateValidatorStrategy {

    boolean isApplicable(ApplicationState newState);

    Answer<String> stateIsAchievable(ApplicationState currentState, ApplicationState newState, @Nullable String reason);
}
