package com.github.gherkin;

import com.github.gherkin.service.ContentResource;
import com.github.gherkin.service.Incrementor;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GuiceModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ContentResource.class);
        binder.bind(Incrementor.class).in(Singleton.class);
    }

    @Provides @Named("sql")
    EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("sql");
    }

    @Provides @Named("sql")
    EntityManager provideEntityManager(EntityManagerFactory factory) {
        return factory.createEntityManager();
    }
}
