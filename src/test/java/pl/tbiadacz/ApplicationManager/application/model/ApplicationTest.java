package pl.tbiadacz.ApplicationManager.application.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.tbiadacz.ApplicationManager.application.common.Answer;

import java.util.stream.Stream;

class ApplicationTest {


    @Test
    void shouldUpdateWhenNameAndContentHasText() {

        //given
        String newName = "newName";
        String newContent = "newContent";
        Application application = new Application("oldName", "oldContent");

        //when
        Answer<String> updateAnswer = application.update(newName, newContent);

        //then
        Assertions.assertThat(updateAnswer.isSuccess()).isTrue();
        Assertions.assertThat(application.getName()).isEqualTo(newName);
        Assertions.assertThat(application.getContent()).isEqualTo(newContent);
    }

    @ParameterizedTest
    @MethodSource("newAttributes")
    void shouldRejectWhenMissingNameOrContent(String name, String content) {

        //given
        Application application = new Application("oldName", "oldContent");

        //when
        Answer<String> updateAnswer = application.update(name, content);

        //then
        Assertions.assertThat(updateAnswer.isSuccess()).isFalse();
    }

    private static Stream<Arguments> newAttributes() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("text", ""),
                Arguments.of("", "text"),
                Arguments.of(null, "text"),
                Arguments.of("text", null),
                Arguments.of(null, null)
        );
    }
}