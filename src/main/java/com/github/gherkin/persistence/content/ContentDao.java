package com.github.gherkin.persistence.content;

import com.github.gherkin.Content;
import com.github.gherkin.persistence.GenericDao;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentDao extends GenericDao<ContentEntity, Content> {

    EntityManager entityManager;

    public ContentDao() {
        EntityManager entityManager = super.entityManagerFactory.createEntityManager();
    }

    @Override
    public Content retrieve(int id) {
        ContentEntity entity = entityManager.find(ContentEntity.class, id);
        return entityToData(entity);
    }

    public Map<String, Content> retrieveAllMap() {
        String queryString = "SELECT c FROM ContentEntity c";
        List<Content> contentList = retrieveAll(queryString);
        Map<String, Content> result = new HashMap<>();

        for(Content content : contentList) {
            result.put(content.get("id"), content);
        }

        return result;
    }

    @Override
    protected ContentEntity dataToEntity(Content content) {
        ContentEntity entity = new ContentEntity();

        entity.setId(Integer.parseInt(content.get("id")));
        entity.setContent(content);

        return entity;
    }

    @Override
    protected Content entityToData(ContentEntity entity) {
        Content content = new Content();
        content.putAll(entity.getContent());

        return content;
    }
}
