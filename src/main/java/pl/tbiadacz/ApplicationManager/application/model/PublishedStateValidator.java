package pl.tbiadacz.ApplicationManager.application.model;

import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

class PublishedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return PUBLISHED.equals(newState);
    }

   public Answer<String> stateIsAchievable(Application application, ApplicationState newState, String reason) {

        if (ACCEPTED.equals(application.getState())) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to" + newState);
    }
}