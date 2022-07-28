package pl.tbiadacz.ApplicationManager.application.model.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.util.stream.Stream;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RejectedStateValidatorTest {

    private RejectedStateValidator validator;

    @BeforeEach
    void setUp() {

        validator = new RejectedStateValidator();
    }

    @ParameterizedTest
    @MethodSource("applicableStates")
    void shouldBeApplicableForAcceptedState(ApplicationState state, boolean applicableResult) {

        //given

        //when
        boolean applicable = validator.isApplicable(state);

        //then
        Assertions.assertThat(applicable).isEqualTo(applicableResult);
    }

    private static Stream<Arguments> applicableStates() {
        return Stream.of(
                Arguments.of(CREATED, false),
                Arguments.of(VERIFIED, false),
                Arguments.of(ACCEPTED, false),
                Arguments.of(DELETED, false),
                Arguments.of(REJECTED, true),
                Arguments.of(PUBLISHED, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStates")
    void shouldCorrectlyChangeState(ApplicationState currentState, boolean success) {

        //given
        String reason = "reason";

        //when
        Answer<String> answer = validator.stateIsAchievable(currentState, REJECTED, reason);

        //then
        Assertions.assertThat(answer.isSuccess()).isEqualTo(success);
    }

    private static Stream<Arguments> provideStates() {
        return Stream.of(
                Arguments.of(CREATED, false),
                Arguments.of(VERIFIED, true),
                Arguments.of(ACCEPTED, true),
                Arguments.of(DELETED, false),
                Arguments.of(REJECTED, false),
                Arguments.of(PUBLISHED, false)
        );
    }

    @Test
    void shouldRejectEmptyReason() {

        //given
        ApplicationState currentState = ACCEPTED;
        String reason = "";

        //when
        Answer<String> answer = validator.stateIsAchievable(currentState, REJECTED, reason);

        //then
        Assertions.assertThat(answer.isSuccess()).isFalse();
    }
}