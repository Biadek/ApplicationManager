package pl.tbiadacz.ApplicationManager.application.model.validation;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;
import pl.tbiadacz.ApplicationManager.application.model.StateValidator;

import java.util.List;

@Service
public class StateChangeValidator implements StateValidator {

    private final List<StateValidatorStrategy> strategies;

    StateChangeValidator(List<StateValidatorStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public Answer<String> canChangeState(ApplicationState currentState, ApplicationState newState, @Nullable String reason) {

        return strategies.stream()
                .filter(strategy -> strategy.isApplicable(newState))
                .findFirst()
                .map(strategy -> strategy.stateIsAchievable(currentState, newState, reason))
                .orElseThrow(UnsupportedClassVersionError::new);
    }
}
