package ru.interview.web.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.*;

/**
 * Created by raster on 17.01.17.
 */
public class JDBCProject {
    private Logger log = LogManager.getLogger(JDBCProject.class);
    public static void main(String[] args) {

    }

    private Connection connect ;

    public JDBCProject (Connection connect) {
        this.connect = connect;
    }


    public HashMap jdbcProjectList() {
        Statement stmt = null;
        ResultSet rs = null;
        HashMap<Integer ,String> result = new HashMap<Integer, String>();
        if (connect != null) {
            try {
                stmt = connect.createStatement();
                rs = stmt.executeQuery("SELECT id, name FROM project");
                while (rs.next()) {
                    Integer key = rs.getInt("id");
                    String value = rs.getString("name");
                    result.put(key, value);
                }
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if ( connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<String> jdbcProjectPage(String id, String rezhim) {
        PreparedStatement pstsm = null;
        ResultSet rs = null;
        List<String> result = new LinkedList<String>();
        if (connect != null ) {
            try  {

                if (rezhim.equals("edit")){
                    pstsm = connect.prepareStatement("SELECT * FROM project WHERE id = ?");
                    pstsm.setInt(1, Integer.parseInt(id));
                    rs = pstsm.executeQuery();
                    while (rs.next()) {
                        result.add(String.valueOf(rs.getDouble("pressure1")));
                        result.add(String.valueOf(rs.getDouble("pressure2")));
                        result.add(String.valueOf(rs.getDouble("checkpipe")));
                        result.add(String.valueOf(rs.getDouble("checkequipment")));
                        result.add(String.valueOf(rs.getInt("tempt1summer")));
                        result.add(String.valueOf(rs.getInt("tempt2summer")));
                        result.add(String.valueOf(rs.getInt("tempt1winter")));
                        result.add(String.valueOf(rs.getInt("tempt2winter")));
                        result.add(rs.getString("name"));
                    }
                    pstsm = connect.prepareStatement("SELECT * FROM systemwater INNER JOIN systems ON systemwater.project_id = ? AND systemwater.systems_id = systems.id;");
                    pstsm.setInt(1, Integer.parseInt(id));
                    rs = pstsm.executeQuery();
                    while (rs.next()) {
                        result.add(rs.getString("name"));
                        result.add(String.valueOf(rs.getDouble("countheat")));
                        result.add(String.valueOf(rs.getInt("tempt11")));
                        result.add(String.valueOf(rs.getInt("tempt21")));
                        result.add(String.valueOf(rs.getDouble("heightsyst")));
                        result.add(String.valueOf(rs.getInt("dh")));
                        result.add(String.valueOf(rs.getDouble("maxv")));
                        result.add(String.valueOf(rs.getDouble("maxh")));
                        result.add(String.valueOf(rs.getInt("countglikol")));
                        result.add(String.valueOf(rs.getInt("systems_id")));
                    }
                }else if (rezhim.equals("delete")){
                    pstsm = connect.prepareStatement("DELETE FROM project WHERE id = ?");
                    pstsm.setInt(1, new Integer(id));
                    pstsm.executeUpdate();
                }
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if ( connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public void jdbcCalculate(HttpServletRequest req, String id) {
        Statement stmt = null;
        PreparedStatement pstsm = null;
        ResultSet rs = null;
        Integer idKey = Integer.parseInt(id);
        try {
            String name = req.getParameter("name");
            Integer tempT1winter = Integer.parseInt(req.getParameter("tempT1winter"));
            Integer tempT2winter = Integer.parseInt(req.getParameter("tempT2winter"));
            Integer tempT1summer = Integer.parseInt(req.getParameter("tempT1summer"));
            Integer tempT2summer = Integer.parseInt(req.getParameter("tempT2summer"));
            Double pressure1 = Double.parseDouble (req.getParameter("pressure1"));
            Double pressure2 = Double.parseDouble (req.getParameter("pressure2"));
            Double checkpipe;
            Double checkequipment;
            try {
                checkpipe = Double.parseDouble (req.getParameter("checkpipe"));
            }catch (NullPointerException e){
                checkpipe = 1.0;
            }
            try {
                checkequipment = Double.parseDouble (req.getParameter("checkequipment"));
            }catch (NullPointerException e){
                checkequipment = 1.0;
            }


            if (idKey == 0) {
                pstsm = connect.prepareStatement(
                        "INSERT INTO project (name, tempT1summer, tempT2summer, tempT1winter, tempT2winter, pressure1, pressure2, checkpipe, checkequipment) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstsm.setString(1, name);
                pstsm.setInt(2, tempT1summer);
                pstsm.setInt(3, tempT2summer);
                pstsm.setInt(4, tempT1winter);
                pstsm.setInt(5, tempT2winter);
                pstsm.setDouble(6, pressure1);
                pstsm.setDouble(7, pressure2);
                pstsm.setDouble(8, checkpipe);
                pstsm.setDouble(9, checkequipment);
                pstsm.executeUpdate();
                stmt = connect.createStatement();
                rs = stmt.executeQuery("SELECT id FROM project ORDER BY id desc LIMIT 1");


                while (rs.next()) {
                    idKey = rs.getInt("id");
                }

            }else {

                pstsm = connect.prepareStatement(
                        "UPDATE project SET name = ?, tempT1summer = ?, tempT2summer = ?, tempT1winter = ?, tempT2winter = ?, pressure1 = ?, pressure2 = ?, checkpipe = ?, checkequipment = ? WHERE id = ?");
                pstsm.setString(1, name);
                pstsm.setInt(2, tempT1summer);
                pstsm.setInt(3, tempT2summer);
                pstsm.setInt(4, tempT1winter);
                pstsm.setInt(5, tempT2winter);
                pstsm.setDouble(6, pressure1);
                pstsm.setDouble(7, pressure2);
                pstsm.setDouble(8, checkpipe);
                pstsm.setDouble(9, checkequipment);
                pstsm.setInt(10, Integer.parseInt(id));
                pstsm.executeUpdate();

                pstsm = connect.prepareStatement("DELETE FROM systemwater WHERE project_id=?");
                pstsm.setInt(1, new Integer(id));
                pstsm.executeUpdate();

            }

            List<String> listParam = new LinkedList<String>();

            Enumeration en = req.getParameterNames();

            while (en.hasMoreElements()) {

                String nameEl = (String) en.nextElement();
                if (nameEl.contains("system")) {
                    String nameSystem = req.getParameter(nameEl);
                    listParam.add(nameSystem);
                } else if (nameEl.contains("countheat")) {
                    String countheat = req.getParameter(nameEl);
                    listParam.add(countheat);
                } else if (nameEl.contains("tempt11")) {
                    String tempt11 = req.getParameter(nameEl);
                    listParam.add(tempt11);
                } else if (nameEl.contains("tempt21")) {
                    String tempt21 = req.getParameter(nameEl);
                    listParam.add(tempt21);
                } else if (nameEl.contains("heightsyst")) {
                    String heightsyst = req.getParameter(nameEl);
                    listParam.add(heightsyst);
                } else if (nameEl.contains("dh")) {
                    String dh = req.getParameter(nameEl);
                    listParam.add(dh);
                } else if (nameEl.contains("countglikol")) {
                    String countglikol = req.getParameter(nameEl);
                    listParam.add(countglikol);
                } else if (nameEl.contains("maxv")) {
                    String maxv = req.getParameter(nameEl);
                    listParam.add(maxv);
                } else if (nameEl.contains("maxh")) {
                    String maxh = req.getParameter(nameEl);
                    listParam.add(maxh);
                }
            }
            for (int i = 0; i < listParam.size() - 2; i += 7) {
                pstsm = connect.prepareStatement(
                        "INSERT INTO systemwater (project_id, countheat, tempt11, tempt21, heightsyst, dh, maxv, maxh, countglikol, systems_id ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
                pstsm.setInt(1, idKey);
                pstsm.setDouble(2, Double.parseDouble(listParam.get(i + 1)));
                pstsm.setInt(3, Integer.parseInt(listParam.get(i + 2)));
                pstsm.setInt(4, Integer.parseInt(listParam.get(i + 3)));
                pstsm.setDouble(5, Double.parseDouble(listParam.get(i + 4)));
                pstsm.setDouble(6, Double.parseDouble(listParam.get(i + 5)));
                pstsm.setDouble(7, Double.parseDouble(listParam.get(listParam.size() - 2)));
                pstsm.setDouble(8, Double.parseDouble(listParam.get(listParam.size() - 1)));
                pstsm.setDouble(9, Double.parseDouble(listParam.get(i + 6)));
                pstsm.setInt(10, Integer.parseInt(listParam.get(i)));
                pstsm.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if ( connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
