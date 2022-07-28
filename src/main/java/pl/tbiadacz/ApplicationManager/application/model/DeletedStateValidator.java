package pl.tbiadacz.ApplicationManager.application.model;

import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

class DeletedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return DELETED.equals(newState);
    }

    public Answer<String> stateIsAchievable(Application application, ApplicationState newState, String reason) {

        if (CREATED.equals(application.getState()) && hasText(reason)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to" + newState);
    }
}
