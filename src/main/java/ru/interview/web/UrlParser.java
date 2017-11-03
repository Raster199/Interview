package ru.interview.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.interview.web.pages.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by raster on 13.12.16.
 */
public class UrlParser {
    private final String baseUrl = "http://localhost:80/Interview/";
    private final String baseDir = "/home/newadmin/IdeaProjects/Interview/content/";
    private static Logger log = LogManager.getLogger(UrlParser.class.getName());
    private Connection connect(){
        String urlJdbc = "jdbc:postgresql://localhost:5432/postgres";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(urlJdbc, "postgres", "postgres");
            return conn;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnect(){
        return this.connect();
    }

    public String getBaseUrl(){
        return this.baseUrl;
    }

    public String getBaseDir() { return baseDir; }

    public Page getPage(HttpServletRequest req) {
        String url = req.getRequestURL().toString().substring(baseUrl.length()+2);
        log.info("URL " + url);

        String[] parseUrl = url.split("/");
        Page page = null;
        if (parseUrl[0].equals("opros")) {
            page = new Opros();
        }else if (parseUrl[0].equals("log")) {
            page = new LogginPage();
        }else if (parseUrl[0].equals("questionary")) {
            page = new Opros(req.getParameter("key"), connect());
        }else if (parseUrl[0].equals("editor")) {
            page = new Editor();
        }else if (parseUrl[0].equals("listSurvey")) {
            page = new ListSurvey(connect(),parseUrl[0],null);
        }else if (parseUrl[0].equals("delRowList")) {
            page = new ListSurvey(connect(),parseUrl[0],req);
        }else if (parseUrl[0].equals("saveEmployee")) {
            page = new ListSurvey(connect(),parseUrl[0],req);
        }

        return page;
    }

    public Page postPage (HttpServletRequest req){
        String url = req.getRequestURL().toString().substring(baseUrl.length()+2);
        log.info("URL " + url);
        Page page = null;
        String[] parseUrl = url.split("/");

        if (parseUrl[0].equals("savedata")) {
            try {
                new QueryToBase(connect()).insertValueUser(req);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (parseUrl[0].equals("saveTemplate")) {

            req.getParameter("nameSurvey");

            try {
                new QueryToBase(connect()).insertValueTemplate(req);
                page = new LogginPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (parseUrl[0].equals("saveEmployee")) {

            //req.getParameter("nameSurvey");

            new QueryToBase(connect()).saveEmployee(req);
            page = new LogginPage();

        }else if (parseUrl[0].equals("sendSurveyFromEmployee")) {

            new Opros(req).sendSurveyFromEmployee();
            page = new LogginPage();
        }

        return page;
    }

}
