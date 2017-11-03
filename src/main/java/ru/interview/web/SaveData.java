package ru.interview.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.interview.web.pages.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;

public class SaveData implements Page {

    private Logger log = LogManager.getLogger(SaveData.class);

    private Connection connect;
    private HttpServletRequest request;

    public SaveData(Connection connection) {
        this.connect = connection;
    }

    public SaveData(Connection connection, HttpServletRequest req) {
        this.connect = connection;
        this.request = req;
    }

    public String getHtml() throws IOException {
        QueryToBase qb = new QueryToBase(connect);
        String result = null;


        try {
            qb.insertValueUser(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
