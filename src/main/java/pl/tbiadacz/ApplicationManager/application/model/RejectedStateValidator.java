package pl.tbiadacz.ApplicationManager.application.model;

import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

class RejectedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return REJECTED.equals(newState);
    }

    public Answer<String> stateIsAchievable(Application application, ApplicationState newState, String reason) {

        ApplicationState currentState = application.getState();

        if ((VERIFIED.equals(currentState) || ACCEPTED.equals(currentState)) && hasText(reason)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to" + newState);
    }
}
