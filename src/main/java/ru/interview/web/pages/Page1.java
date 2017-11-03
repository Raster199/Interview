package ru.interview.web.pages;

import ru.interview.web.FileWorker;

import java.io.FileNotFoundException;

/**
 * Created by raster on 13.12.16.
 */
public class Page1 implements Page {
    public String getHtml() throws FileNotFoundException {
        String file = FileWorker.read("/home/raster/IdeaProjects/ServletTest/src/main/resources/page.html");

//        String str =
//                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
//                "<html>\n" +
//                " <head>\n" +
//                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
//                "  <title>Пример веб-страницы</title>\n" +
//                " </head>\n" +
//                " <body>\n" +
//                "  <h1>Заголовок</h1>\n" +
//                "  <!-- Комментарий -->\n" +
//                "  <p>Первый абзац.</p>\n" +
//                "  <p>Второй абзац.</p>\n" +
//                " </body>\n" +
//                "</html>";
        return file;
    }
}
