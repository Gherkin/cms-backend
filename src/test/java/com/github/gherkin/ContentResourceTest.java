package com.github.gherkin;

import com.github.gherkin.persistence.content.ContentDao;
import com.github.gherkin.service.ContentResource;
import com.github.gherkin.service.Incrementor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ContentResourceTest {

    @Mock
    ContentDao dao;
    @Mock
    ChangeLog log;
    @InjectMocks
    private ContentResource target;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        target.setNextId(new Incrementor());
        target.setContentMap(new HashMap<String, Content>());
    }

    @Test
    public void fetch() throws Exception {
        Content input = initializeContent();

        Content output = target.fetch("1");

        assertEquals(input, output);
    }

    @Test(expected = WebApplicationException.class)
    public void fetchNull() throws Exception {
        target.fetch(null);
    }

    @Test(expected = WebApplicationException.class)
    public void fetchNonExistant() throws Exception {
        target.fetch("9");
    }

    @Test
    public void insertAssignsId() throws Exception {
        Content content = new Content();
        target.insert(content);

        assertTrue("Expected inserted content to have id", content.containsKey("id"));
    }

    @Test(expected = WebApplicationException.class)
    public void insertNull() throws Exception {
        target.insert(null);
    }

    @Test
    public void update() throws Exception {
        initializeContent();

        Content input = new Content();
        input.put("test", "no");

        target.update("1", input);

        Map<String, Content> contentMap = target.getContentMap();
        Content output = contentMap.get("1");

        assertEquals(input, output);
    }

    @Test(expected = WebApplicationException.class)
    public void updateNonExistant() throws Exception {

        target.update("8", new Content());
    }

    @Test(expected = WebApplicationException.class)
    public void updateNull() throws Exception {

        target.update(null, null);
    }

    @Test
    public void remove() throws Exception {
        initializeContent();

        target.remove("1");

        Map<String, Content> contentMap = target.getContentMap();

        assertTrue("input content shouldnt exist", !contentMap.containsKey("1"));
    }

    @Test(expected = WebApplicationException.class)
    public void removeNonExistant() throws Exception {

        target.remove("8");
    }

    @Test(expected = WebApplicationException.class)
    public void removeNull() throws Exception {

        target.remove(null);
    }

    private Content initializeContent() {
        Content input = new Content();
        input.put("test", "yes");

        Map<String, Content> contentMap = new HashMap<>();
        contentMap.put("1", input);
        target.setContentMap(contentMap);

        return input;
    }


}
