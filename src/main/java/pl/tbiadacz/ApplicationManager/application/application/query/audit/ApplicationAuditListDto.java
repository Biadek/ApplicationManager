package pl.tbiadacz.ApplicationManager.application.application.query.audit;

import org.springframework.lang.Nullable;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationNumber;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class ApplicationAuditListDto implements Serializable {

    private static final long serialVersionUID = 1;

    private final ApplicationId id;
    private final String name;
    private final String content;
    private final ApplicationState state;
    private final String rejectionReason;
    private final ApplicationNumber number;
    private final LocalDateTime changeDateTime;
    private final RevisionChangeType type;

    public ApplicationAuditListDto(ApplicationId id,
                                   String name,
                                   String content,
                                   ApplicationState state,
                                   @Nullable String rejectionReason,
                                   @Nullable Long number,
                                   LocalDateTime changeDateTime,
                                   RevisionChangeType type) {

        this.id = id;
        this.name = name;
        this.content = content;
        this.state = state;
        this.rejectionReason = rejectionReason;
        this.number = Optional.ofNullable(number).map(ApplicationNumber::of).orElse(null);
        this.changeDateTime = changeDateTime;
        this.type = type;
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

    public LocalDateTime getChangeDateTime() {
        return changeDateTime;
    }

    public RevisionChangeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationAuditListDto that = (ApplicationAuditListDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(content, that.content) &&
                state == that.state &&
                Objects.equals(rejectionReason, that.rejectionReason) &&
                Objects.equals(number, that.number) &&
                Objects.equals(changeDateTime, that.changeDateTime) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, state, rejectionReason, number, changeDateTime, type);
    }
}
