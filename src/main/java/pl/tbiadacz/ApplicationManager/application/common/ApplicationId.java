package pl.tbiadacz.ApplicationManager.application.common;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApplicationId implements Serializable {

    private static final long serialVersionUID = 1;

    private Long id;

    private ApplicationId() {}

    private ApplicationId(Long id) {
        this.id = id;
    }

    public static ApplicationId of(Long id) {
        return new ApplicationId(id);
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationId that = (ApplicationId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
