package com.github.gherkin.persistence.changelog;

import com.github.gherkin.persistence.GenericSqlDao;

import java.util.List;

public class ChangeLogDao extends GenericSqlDao<ChangeLogEntry, String> {

    public ChangeLogDao() {
        super(ChangeLogEntry.class);
    }

    public List<String> retrieveAll() {
        String queryString = "SELECT c FROM ChangeLogEntry c";
        return super.retrieveAll(queryString);
    }

    @Override
    protected ChangeLogEntry dataToEntity(String data) {
        return new ChangeLogEntry(data);
    }

    @Override
    protected String entityToData(ChangeLogEntry entity) {
        return entity.toString();
    }
}
