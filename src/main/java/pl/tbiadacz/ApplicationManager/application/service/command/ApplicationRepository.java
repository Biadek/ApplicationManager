package pl.tbiadacz.ApplicationManager.application.service.command;

import org.hibernate.HibernateException;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;
import pl.tbiadacz.ApplicationManager.application.model.Application;

import java.util.Optional;

public interface ApplicationRepository {

    void add(Application application) throws HibernateException;

    Optional<Application> findById(ApplicationId id);

    void update(Application application);
}
