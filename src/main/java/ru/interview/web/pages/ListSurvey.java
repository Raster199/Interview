package ru.interview.web.pages;

import ru.interview.web.QueryToBase;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;

public class ListSurvey implements Page {

    private Connection connect;
    private String url;
    private HttpServletRequest request;

    public ListSurvey(Connection connection, String url, HttpServletRequest request) {
        this.connect = connection;
        this.url = url;
        this.request = request;
    }


    public String getHtml() throws IOException {

        if (url.equals("listSurvey")){

            return new QueryToBase(connect).listSurvey();

        }else if (url.equals("delRowList")){

            int idSurvey = Integer.parseInt(request.getParameter("idSurvey"));

            new QueryToBase(connect).delRowList(idSurvey);

            return null;

        }

        return null;

    }
}
