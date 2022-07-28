package pl.tbiadacz.ApplicationManager.application.service.query;

import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;

import java.io.Serializable;

public class ApplicationListDto implements Serializable {

    private static final long serialVersionUID = 1;

    private final ApplicationId id;
    private final String name;

    public ApplicationListDto(Long id, String name) {

        this.id = ApplicationId.of(id);
        this.name = name;
    }

    public ApplicationId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //TODO euqals, hashcode
}
