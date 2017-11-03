package ru.interview.web.pages;

/**
 * Created by raster on 15.12.16.
 */
public class LogginPage implements Page {

    public String getHtml() {
        String str = "<html><ul>\n" +

                "    <form action=\"loginform\" method=\"post\">" +
                "    <li>" +
                "       <label for=\"username\">Login</label>\n"  +
                "       <input charset=\"UTF-8\" type=\"text\" name=\"username\" placeholder=\"Логин\" required></li>\n" +
                "    <li>" +
                "       <label for=\"password\">Password</label>\n" +
                "       <input type=\"password\" name=\"password\" placeholder=\"password\" required></li>\n" +
                "    <li>\n" +
                "       <input type=\"submit\" value=\"Login\"></li>\n" +
                "  </ul>\n" +
                "</form>" +
                "</html>";
        return str;
    }
}
