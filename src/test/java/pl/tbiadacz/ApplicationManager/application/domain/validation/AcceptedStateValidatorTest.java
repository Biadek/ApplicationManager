package pl.tbiadacz.ApplicationManager.application.domain.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.util.stream.Stream;

import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

class AcceptedStateValidatorTest {

    private static AcceptedStateValidator validator;

    @BeforeAll
    static void setUp() {

        validator = new AcceptedStateValidator();
    }

    @ParameterizedTest
    @MethodSource("applicableStates")
    void shouldBeApplicableForCorrectState(ApplicationState state, boolean applicableResult) {

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
                Arguments.of(ACCEPTED, true),
                Arguments.of(DELETED, false),
                Arguments.of(REJECTED, false),
                Arguments.of(PUBLISHED, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStates")
    void shouldCorrectlyChangeState(ApplicationState currentState, boolean success) {

        //given

        //when
        Answer<String> answer = validator.stateIsAchievable(currentState, ACCEPTED, null);

        //then
        Assertions.assertThat(answer.isSuccess()).isEqualTo(success);
    }

    private static Stream<Arguments> provideStates() {
        return Stream.of(
                Arguments.of(CREATED, false),
                Arguments.of(VERIFIED, true),
                Arguments.of(ACCEPTED, false),
                Arguments.of(DELETED, false),
                Arguments.of(REJECTED, false),
                Arguments.of(PUBLISHED, false)
        );
    }
}