package com.github.gherkin.persistence;


import com.google.inject.Inject;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericSqlDao<EntityType, DataType>{
    @Inject
    private EntityManager entityManager;
    private Class<EntityType> type;

    protected GenericSqlDao(Class<EntityType> type) {
        this.type = type;
    }

    public void save(DataType data) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            EntityType entity = dataToEntity(data);

            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();

        } catch(IllegalArgumentException exception) {
            transaction.rollback();
            exception.printStackTrace();
        }
    }

    public void insert(DataType data) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            EntityType entity = dataToEntity(data);

            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public DataType retrieve(int id) throws NullPointerException {
        EntityType entity = entityManager.find(type, id);
        return entityToData(entity);
    }

    protected List<DataType> retrieveAll(String queryString) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<DataType> result = new ArrayList<>();

        try {
            transaction.begin();

            Query query = entityManager.createQuery(queryString);
            //noinspection unchecked,unchecked
            List<EntityType> entityList = query.getResultList();

            transaction.commit();


            for(EntityType entity : entityList) {
                result.add(entityToData(entity));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void remove(int id) throws NullPointerException{
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            EntityType entity = entityManager.find(type, id);

            if(entity == null) {
                throw new NullPointerException();
            }

            entityManager.remove(entity);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setType(Class<EntityType> type) {
        this.type = type;
    }

    protected abstract EntityType dataToEntity(DataType data);
    protected abstract DataType entityToData(EntityType entity);
}
