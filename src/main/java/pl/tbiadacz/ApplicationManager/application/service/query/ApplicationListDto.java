package pl.tbiadacz.ApplicationManager.application.service.query;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationNumber;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class ApplicationListDto implements Serializable {

    private static final long serialVersionUID = 1;

    private final ApplicationId id;
    private final String name;
    private final String content;
    private final ApplicationState state;
    private final String rejectionReason;
    private final ApplicationNumber number;

    public ApplicationListDto(Long id,
                              String name,
                              String content,
                              ApplicationState state,
                              @Nullable String rejectionReason,
                              @Nullable Long number) {

        this.id = ApplicationId.of(id);
        this.name = name;
        this.content = content;
        this.state = state;
        this.rejectionReason = rejectionReason;
        this.number = Optional.ofNullable(number).map(ApplicationNumber::of).orElse(null);
    }

    public ApplicationId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public ApplicationState getState() {
        return state;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public ApplicationNumber getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationListDto that = (ApplicationListDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(content, that.content) &&
                state == that.state &&
                Objects.equals(rejectionReason, that.rejectionReason) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, state, rejectionReason, number);
    }
}
