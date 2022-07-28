package pl.tbiadacz.ApplicationManager.application.model.validation;

import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

@Service
class VerifiedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return VERIFIED.equals(newState);
    }

    public Answer<String> stateIsAchievable(ApplicationState currentState, ApplicationState newState, String reason) {

        if (CREATED.equals(currentState)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to " + newState);
    }
}
