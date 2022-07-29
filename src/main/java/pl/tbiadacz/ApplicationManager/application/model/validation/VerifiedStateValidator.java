package pl.tbiadacz.ApplicationManager.application.model.validation;

import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.CREATED;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.VERIFIED;

@Service
class VerifiedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return VERIFIED.equals(newState);
    }

    @Override
    public Answer<String> stateIsAchievable(ApplicationState currentState, ApplicationState newState, String reason) {

        if (CREATED.equals(currentState)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to " + newState);
    }
}
