package pl.tbiadacz.ApplicationManager.application.model;

import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

class AcceptedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return ACCEPTED.equals(newState);
    }

    public Answer<String> stateIsAchievable(Application application, ApplicationState newState, String reason) {

        if (VERIFIED.equals(application.getState())) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to" + newState);
    }
}
