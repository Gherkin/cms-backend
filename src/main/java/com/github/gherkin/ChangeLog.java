package com.github.gherkin;

import com.github.gherkin.persistence.changelog.ChangeLogDao;
import com.google.inject.Inject;

import java.util.List;

public class ChangeLog {
    @Inject
    ChangeLogDao dao;

    public void add(String s) {
        dao.save(s);
    }

    public List<String> get(int i) {
        List<String> changeLog = dao.retrieveAll();
        return changeLog.subList(i, changeLog.size() - 1);
    }
}
