package pl.tbiadacz.ApplicationManager.application.service;

import java.io.Serializable;
import java.util.Objects;

public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1;

    private final String reason;

    public ErrorDto(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(reason, errorDto.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason);
    }

    @Override
    public String toString() {
        return reason;
    }
}
