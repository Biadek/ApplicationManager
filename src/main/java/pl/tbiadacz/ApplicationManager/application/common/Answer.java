package pl.tbiadacz.ApplicationManager.application.common;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Answer<FAILURE> {

    private final FAILURE failure;


    private Answer(FAILURE failure) {

        this.failure = failure;
    }

    public static <FAILURE> Answer<FAILURE> success() {
        return new Answer<>(null);
    }

    public static <FAILURE> Answer<FAILURE> failure(FAILURE failure) {
        return new Answer<>(failure);
    }

    public boolean isSuccess() {
        return failure == null;
    }

    public void ifFailure(Consumer<? super FAILURE> action) {

        Optional.ofNullable(failure).ifPresent(action);
    }

    public Answer<FAILURE> ifSuccess(Runnable action) {

        if (isSuccess()) {
            action.run();
        }

        return this;
    }

    public <EXCEPTION extends RuntimeException> void throwIfFailure(Function<FAILURE, EXCEPTION> function) {

        ifFailure(message -> {
            throw function.apply(message);
        });
    }

}
