package pl.tbiadacz.ApplicationManager.application.domain.validation;

import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.ACCEPTED;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.VERIFIED;

@Service
class AcceptedStateValidator implements StateValidatorStrategy {

    @Override
    public boolean isApplicable(ApplicationState newState) {
        return ACCEPTED.equals(newState);
    }

    @Override
    public Answer<String> stateIsAchievable(ApplicationState currentState, ApplicationState newState, String reason) {

        if (VERIFIED.equals(currentState)) {
            return Answer.success();
        }

        return Answer.failure("Can not change state to " + newState);
    }
}
