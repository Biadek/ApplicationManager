package pl.tbiadacz.ApplicationManager.application.model;

import pl.tbiadacz.ApplicationManager.application.common.Answer;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationState;

import javax.persistence.*;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;
import static pl.tbiadacz.ApplicationManager.application.common.ApplicationState.*;

@Entity
public class Application {

    public static final String D_ID = "id";
    public static final String D_NAME = "name";
    public static final String D_STATE = "state";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @AttributeOverride(name = D_ID, column = @Column(name = "id"))
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationState state;

    private String rejectionReason;

    private Application() {}

    public Application(String name, String content) {
        this.name = name;
        this.content = content;
        this.state = CREATED;
        this.rejectionReason = null;
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
        //TODO nadaj numer
        return validator.canChangeState(state, PUBLISHED, null)
                .ifSuccess(() -> this.state = PUBLISHED);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(content, that.content) &&
                state == that.state &&
                Objects.equals(rejectionReason, that.rejectionReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, content, state, rejectionReason);
    }
}
