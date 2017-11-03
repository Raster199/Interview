package ru.interview.web.pages;

import ru.interview.web.FileWorker;
import java.io.FileNotFoundException;

/**
 * Created by raster on 16.02.17.
 */
public class MainPage implements Page {

    public String getHtml() throws FileNotFoundException {
        String shablon = FileWorker.read("/home/raster/IdeaProjects/ServletTest/src/main/resources/site.html");
        String putInShablon = "<h2>Расчет оборудования теплового пункта</h2>"+
                "<li>Данный проект разрабатывался в учебных целях. Он предназначен для ускорения работы инженеров-проектировщиков."+
                "Все расчеты этого калькулятора выполняются в соответствии с СП 41-101-95 \"Проектиование тепловых пунктов\" ";
        return shablon.replaceAll("\\{\\{name\\}\\}", putInShablon);
    }
}
