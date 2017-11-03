package ru.interview.web.pages;

import ru.interview.web.FileWorker;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Created by raster on 16.01.17.
 */
public class ProjectList implements Page{

    private Connection connection;

    public ProjectList(Connection connection){
        this.connection = connection;
    }

    public String getHtml() throws FileNotFoundException {
        JDBCProject jp = new JDBCProject(connection);
        return editHtml(jp.jdbcProjectList());
    }

    public String editHtml(HashMap<Integer, String> hash) throws FileNotFoundException {

        String shablon = FileWorker.read("/home/raster/IdeaProjects/ServletTest/src/main/resources/site.html");
        String putInShablon = "";
        String urlEdit = "<a href=\"http://localhost:80/ServletTest/Project/edit/id/";
        String urlendEdit = "\">Редактировать</a>";
        String urlDelete = "<a href=\"http://localhost:80/ServletTest/Project/delete/id/";
        String urlendDelete = "\">Удалить</a>";
        putInShablon += "<table  border='1' cellpadding='4' cols='5' class='table_calc' ><caption>Список проектов</caption><tr><td>№</td><td coolspan=3>Наименование объекта</td></tr>";
        try{
/*            for(Integer key:  hash.keySet() ){
                String name = hash.get(key);
            }*/
            int i = 1;
            for(HashMap.Entry<Integer, String> entry: hash.entrySet()){

                putInShablon += "\\\n<tr><td>" + i++ + "</td><td width='600px'>" + entry.getValue() +  "</td><td>" + urlEdit + entry.getKey() + urlendEdit + "</td><td>" + urlDelete + entry.getKey() + urlendDelete + "</td></tr>\\\n"  ;
            }
            putInShablon += "</table>";

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return shablon.replaceAll("\\{\\{name\\}\\}", putInShablon);
    }
}
