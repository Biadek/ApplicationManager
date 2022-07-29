package pl.tbiadacz.ApplicationManager.application.model;

import org.hibernate.HibernateException;
import pl.tbiadacz.ApplicationManager.application.common.ApplicationId;

import java.util.Optional;

public interface ApplicationRepository {

    void add(Application application) throws HibernateException;

    Optional<Application> findById(ApplicationId id);

    void update(Application application);
}
