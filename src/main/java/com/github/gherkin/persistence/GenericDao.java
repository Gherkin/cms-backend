package com.github.gherkin.persistence;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDao<EntityType, DataType> {
    protected EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sql");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

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

    public DataType retrieve(int id) {
        return null;
    }

    public List<DataType> retrieveAll(String queryString) {
        EntityTransaction transaction = entityManager.getTransaction();
        List<DataType> result = new ArrayList<>();

        try {
            transaction.begin();

            Query query = entityManager.createQuery(queryString);
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

    public void remove(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            DataType data = retrieve(id);
            EntityType entity = dataToEntity(data);

            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected abstract EntityType dataToEntity(DataType data);
    protected abstract DataType entityToData(EntityType entity);
}
