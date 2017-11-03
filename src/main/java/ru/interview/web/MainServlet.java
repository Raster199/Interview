package ru.interview.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.interview.web.pages.Page;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/")
public class MainServlet extends HttpServlet {
    private static Logger log = LogManager.getLogger(MainServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.info(" --- doGet ---");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();
        UrlParser pars = new UrlParser();
        Page page = pars.getPage(req);

        if(page != null){
            String html = page.getHtml();
            pw.print(html);
        }
        pw.close();
    }

    public void doPost (HttpServletRequest req, HttpServletResponse res) throws IOException{
        log.info(" --- doPost ---");
        res.setCharacterEncoding("UTF-8");
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        PrintWriter pw = res.getWriter();
        UrlParser pars = new UrlParser();
        Page page = pars.postPage(req);
        if(page != null) {
            String html = page.getHtml();
            pw.print(html);
        }
        pw.close();
    }
}