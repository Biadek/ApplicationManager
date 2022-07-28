package pl.tbiadacz.ApplicationManager.application.model;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

public interface StateValidator {
    Answer<String> canChangeState(ApplicationState currentState, ApplicationState newState, @Nullable String reason);
}
