package ru.interview.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.interview.web.FileWorker;
import ru.interview.web.QueryToBase;
import ru.interview.web.SenderMail;
import ru.interview.web.UrlParser;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.sql.Connection;

public class Opros implements Page {

    private String key;
    private Connection connection;
    private String file;
    private HttpServletRequest request;

    private Logger log = LogManager.getLogger(Opros.class);

    public Opros() {
    }

    public Opros(HttpServletRequest request) {
        this.request = request;
    }

    public Opros(String key,Connection connection) {
        this.key = key;
        this.connection = connection;
    }

    public String getHtml() throws FileNotFoundException {

        String baseDir = new UrlParser().getBaseDir();
        QueryToBase queryToBase = new QueryToBase(connection);
        String flag = queryToBase.checkKey(key);

        if (flag == null){

            return null;

        }else if (flag.equals("notVoted")){

            String strFile = FileWorker.read( baseDir + "opros_2.html");
            return strFile.replaceAll("\\{\\{keyPars\\}\\}", key);

        }else if (flag.equals("voted")){

            file = FileWorker.read(baseDir + "voted.html");
            return file;

        }else if (flag.equals("false")){

            file = FileWorker.read(baseDir + "notKey.html");
            return file;
        }

        return null;

    }

    public void sendSurveyFromEmployee(){

        String email = request.getParameter("email");
        log.info("email " + email);
        SenderMail mail = new SenderMail("babayka@balalayka.ru", null);
        mail.send("Test mail", mail.textInMail(),  email, "babayka@balalayka.ru" );

    }

}
