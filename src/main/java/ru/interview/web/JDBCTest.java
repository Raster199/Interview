package ru.interview.web;

import java.sql.*;

/**
 * Created by raster on 12.01.17.
 */
public class JDBCTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://127.0.0.1:5432/interview";
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException  e){
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(url, "postgres","postgres");

            if(conn != null){
               stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * from project");
               String table = "<table>";
                while(rs.next()){
                    table += String.valueOf(rs.getInt("id"));
                    table += rs.getString("name");
                }
//               while(rs.next()){
//                   table+="<tr>";
//                   table +="<td>"+ String.valueOf(rs.getInt("id")+"</td>");
//                   table +="<td>"+ rs.getString("name")+"</td>";
//                   table+="</tr>";
//               }
//               table+="</table>";
               System.out.println(table);

               pstmt = conn.prepareStatement("SELECT * from systems WHERE id >= ?");
               pstmt.setInt(1,2);
               rs = pstmt.executeQuery();
               table = "<table>";
                while(rs.next()){
                    table+="<tr>";
                    table +="<td>"+ String.valueOf(rs.getInt("id")+"</td>");
                    table +="<td>"+ rs.getString("name")+"</td>";
                    table+="</tr>";
                }
                table+="</table>";
                System.out.println(table);


            }else{

            }

            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}

