package ru.interview.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class QueryToBase {

    private Logger log = LogManager.getLogger(QueryToBase.class);

    public QueryToBase(Connection connect) {
        this.connect = connect;
    }

    private Connection connect;

    public void insertValueUser(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();
        StringBuilder buffer = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(buffer.toString(), JsonNode.class); // парсинг текста
        String question1 = rootNode.get("question1").asText(); // получение строки из поля "question1"
        String question3 = rootNode.get("question3").asText(); // получение строки из поля "question3"
        String key = rootNode.get("key").asText();
        //JsonNode childNode =  rootNode.get("place"); // получаем объект Place

        PreparedStatement prst = null;
        ResultSet rs = null;
        String result = "";
        if (connect != null){
            try{
                prst = connect.prepareStatement("INSERT INTO answer (numQuestion, numAnswer) values (?,?)");
                prst.setString(1,question1);
                prst.setString(2,question3);
                prst.executeUpdate();
                prst = connect.prepareStatement("UPDATE accessKey SET entry = true WHERE key = ?");
                prst.setString(1,key);
                prst.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertValueTemplate(HttpServletRequest request) throws IOException {

        String nameSurvey = request.getParameter("nameSurvey");

        Map<String, String[]> requestParamMap = request.getParameterMap();

        PreparedStatement prst;
        ResultSet rs = null;
        int numQuestion = 1;
        int numAnswer = 1;
        int idQuestion = 0;
        int idSurvey = 0;

        if (connect != null){

            try{

                prst = connect.prepareStatement("INSERT INTO survey (title) values (?)");
                prst.setString(1,nameSurvey);
                prst.executeUpdate();
                prst = connect.prepareStatement("SELECT id FROM survey WHERE title like ?");
                prst.setString(1,nameSurvey);
                rs = prst.executeQuery();

                while (rs.next()) {
                    idSurvey = rs.getInt("id");
                }

                for (Map.Entry<String, String[]> question : requestParamMap.entrySet() ) {


                    if (question.getKey().equals("question" + numQuestion)){

                        String nameQuestion = question.getValue()[0];

                        prst = connect.prepareStatement("INSERT INTO question (title,id_survey) values (?,?)");
                        prst.setString(1,nameQuestion);
                        prst.setInt(2,idSurvey);
                        prst.executeUpdate();

                        prst = connect.prepareStatement("SELECT id FROM question WHERE title like ? AND id_survey = ?");
                        prst.setString(1,nameQuestion);
                        prst.setInt(2,idSurvey);
                        rs = prst.executeQuery();

                        String typeAnswerValue = "";
                        boolean isRequired = false;

                        while (rs.next()) {
                            idQuestion = rs.getInt("id");
                        }


                        for (Map.Entry<String, String[]> typeAnswer : requestParamMap.entrySet() ) {

                            if (typeAnswer.getKey().equals("isRequired" + numQuestion)) {
                                if (!typeAnswer.getValue()[0].equals("")){
                                    isRequired = true;
                                }
                            }else if (typeAnswer.getKey().equals("typeAnswer" + numQuestion)){
                                typeAnswerValue = typeAnswer.getValue()[0];
                            }
                        }


                        for (Map.Entry<String, String[]> answer : requestParamMap.entrySet() ) {

                            if (answer.getKey().equals("answer" + numQuestion + numAnswer)){

                                String nameAnswer = answer.getValue()[0];

                                prst = connect.prepareStatement("INSERT INTO answer (title,id_question,typeAnswer,required) values (?,?,?,?)");
                                prst.setString(1,nameAnswer);
                                prst.setInt(2,idQuestion);
                                prst.setString(3,typeAnswerValue);
                                prst.setBoolean(4,isRequired);
                                prst.executeUpdate();

                                numAnswer ++;

                            }
                        }
                        numQuestion ++;
                        numAnswer = 1;
                    }

                }

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertKey(String key){

        PreparedStatement prst = null;
        if (connect != null){
            try{
                prst = connect.prepareStatement("INSERT INTO accessKey (key,entry) values (?,false)");
                prst.setString(1,key);
                prst.executeUpdate();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String checkKey(String key){
        Statement stmt;
        ResultSet rs;
        if (connect != null){
            try{
                stmt = connect.createStatement();
                rs = stmt.executeQuery("SELECT key,entry FROM accessKey WHERE key LIKE '" + key + "'");
                if (rs.next()){
                    if((rs.getString("key") != null) && (rs.getBoolean("entry"))) {

                        return "voted";
                        //update таблицы accesskey в бд

                    }else if((rs.getString("key") != null) && (!rs.getBoolean("entry"))) {

                        return "notVoted";
                    }
                }else{
                    return "false";
                }

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String listSurvey(){
        Statement stmt;
        ResultSet rs;
        String tableShablon = "";
        if (connect != null){
            try{
                stmt = connect.createStatement();
                tableShablon += "<table  border='1' cellpadding='4' cols='5' id='tableSurvey' ><caption>Список опросов</caption><tr><th>№</th><th colspan=3>Наименование опроса</th></tr>";
                rs = stmt.executeQuery("SELECT id,title FROM survey");

                int num = 1;

                while (rs.next()){

                    tableShablon += "<tr><td>" + num++ + "</td><td><input type='hidden' value='" + rs.getInt("id") + "'></td><td>" + rs.getString("title") +"</td><td><input type='button' value='удалить' onclick=delRowList(this)></td></tr>";
                    //<td><input type='button' value='редактировать' onclick=editRowList()></td><input type='button' value='удалить' onclick=delRowList()>

                }
                tableShablon += "</table>";
                return tableShablon;


            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String delRowList(int idSurvey){

        PreparedStatement prst = null;

        if (connect != null){
            try{
                prst = connect.prepareStatement("DELETE FROM survey WHERE id = ?");
                prst.setInt(1,idSurvey);
                prst.executeUpdate();
                prst.close();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String saveEmployee(HttpServletRequest request){

        String firstname = request.getParameter("firstname");
        String middlename = request.getParameter("middlename");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");

        PreparedStatement prst = null;

        if (connect != null){
            try{
                prst = connect.prepareStatement("INSERT INTO users (firstname, middlename, lastname, email) values (?,?,?,?);");
                prst.setString(1,firstname);
                prst.setString(2,middlename);
                prst.setString(3,lastname);
                prst.setString(4,email);
                prst.executeUpdate();
                prst.close();

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}