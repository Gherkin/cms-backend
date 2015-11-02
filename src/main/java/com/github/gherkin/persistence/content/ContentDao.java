package com.github.gherkin.persistence.content;

import com.github.gherkin.Content;
import com.github.gherkin.persistence.GenericSqlDao;
import com.google.inject.Inject;

import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentDao extends GenericSqlDao<ContentEntity, Content> {
    @Inject
    EntityManager entityManager;

    public @Inject ContentDao() {
        super(ContentEntity.class);
    }

    public Map<String, Content> retrieveAllMap() {
        List<Content> contentList = retrieveAll();
        Map<String, Content> result = new HashMap<>();

        for(Content content : contentList) {
            result.put(content.get("id"), content);
        }

        return result;
    }

    public List<Content> retrieveAll() {
        String queryString = "SELECT c FROM ContentEntity c";
        return retrieveAll(queryString);
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
