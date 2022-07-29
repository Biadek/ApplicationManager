package pl.tbiadacz.ApplicationManager.application.common;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationNumber implements Serializable {

    private static final long serialVersionUID = 1;

    private final Long number;

    private ApplicationNumber(Long number) {
        this.number = number;
    }

    public static ApplicationNumber of(Long number) {
        return new ApplicationNumber(number);
    }

    public Long getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationNumber that = (ApplicationNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
