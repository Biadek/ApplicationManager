package pl.tbiadacz.ApplicationManager.application.domain;

import org.hibernate.envers.Audited;
import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import javax.persistence.*;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

@Entity
@Audited
public class Application {

    public static final String D_ID = "id";
    public static final String D_NAME = "name";
    public static final String D_CONTENT = "content";
    public static final String D_STATE = "state";
    public static final String D_REJECTION_REASON = "rejectionReason";
    public static final String D_UNIQUE_NUMBER = "uniqueNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationState state;

    private String rejectionReason;

    private Long uniqueNumber;

    private Application() {
    }

    public Application(String name, String content) {
        this.name = name;
        this.content = content;
        this.state = CREATED;
        this.rejectionReason = null;
        this.uniqueNumber = null;
    }

    public Answer<String> update(String name, String content) {

        return canUpdate(name, content)
                .ifSuccess(() -> {
                            this.name = name;
                            this.content = content;
                        }
                );
    }

    public Answer<String> reject(String rejectionReason, StateValidator validator) {

        return validator.canChangeState(state, REJECTED, rejectionReason)
                .ifSuccess(() -> {
                    this.state = REJECTED;
                    this.rejectionReason = rejectionReason;
                });
    }

    public Answer<String> delete(String deletionReason, StateValidator validator) {

        return validator.canChangeState(state, DELETED, deletionReason)
                .ifSuccess(() -> {
                    this.state = DELETED;
                    this.rejectionReason = deletionReason;
                });
    }

    public Answer<String> verify(StateValidator validator) {

        return validator.canChangeState(state, VERIFIED, null)
                .ifSuccess(() -> this.state = VERIFIED);
    }

    public Answer<String> publish(StateValidator validator) {
        return validator.canChangeState(state, PUBLISHED, null)
                .ifSuccess(() -> {
                    this.state = PUBLISHED;
                    this.uniqueNumber = id;
                });
    }

    public Answer<String> accept(StateValidator validator) {

        return validator.canChangeState(state, ACCEPTED, null)
                .ifSuccess(() -> this.state = ACCEPTED);
    }

    private Answer<String> canUpdate(String name, String content) {

        if (!CREATED.equals(state) && !VERIFIED.equals(state)) {
            return Answer.failure("Can not update Application in state " + state);
        }
        if (!hasText(name)) {
            return Answer.failure("Name must not be empty");
        }
        if (!hasText(content)) {
            return Answer.failure("Content must not be empty");
        }

        return Answer.success();
    }

    public ApplicationId getId() {
        return ApplicationId.of(id);
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

    public Long getUniqueNumber() {
        return uniqueNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(content, that.content) &&
                state == that.state &&
                Objects.equals(rejectionReason, that.rejectionReason) &&
                Objects.equals(uniqueNumber, that.uniqueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, state, rejectionReason, uniqueNumber);
    }
}
