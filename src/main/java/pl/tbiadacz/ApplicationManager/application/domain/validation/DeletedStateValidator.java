package pl.tbiadacz.ApplicationManager.application.domain.validation;

import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

@Service
class DeletedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return DELETED.equals(newState);
    }

    @Override
    public Answer<String> stateIsAchievable(ApplicationState currentState, ApplicationState newState, String reason) {

        if (CREATED.equals(currentState) && hasText(reason)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to " + newState);
    }
}
