package com.github.gherkin;

import com.github.gherkin.persistence.content.ContentDao;
import com.github.gherkin.service.ContentResource;
import com.github.gherkin.service.Incrementor;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GuiceModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ContentResource.class);
    }

    @Provides @Named("sql")
    EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("sql");
    }

    @Provides
    @Inject
    EntityManager provideEntityManager(@Named("sql") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Provides
    @Inject
    Incrementor provideIncrementor(ContentDao dao) {
        List<Content> contentList = dao.retrieveAll();
        return new Incrementor(contentList.size());
    }
}
