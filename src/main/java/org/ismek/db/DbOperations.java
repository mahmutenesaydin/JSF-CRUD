package org.ismek.db;

import com.sun.research.ws.wadl.Request;
import org.ismek.beans.RehberManagedBean;
import org.ismek.domain.Rehber;
import java.lang.String.*;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKARTAL on 4.3.2021.
 */
public class DbOperations {

    //static String conUrl="jdbc:mysql://localhost/deneme?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false";
    static String conUrl="jdbc:mysql://localhost/deneme?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    static String conUser="root";
    static String conPass="12345";

    static {
        classForName();
    }
    
    public static List<Rehber> tumRehberiGetir() {
        List<Rehber> rehberList = new ArrayList<>();
        String sql = "SELECT * FROM REHBER";

        try (
                Connection con= DriverManager.getConnection(conUrl,conUser,conPass);
                PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("isim");
                String phone = resultSet.getString("telefon");
                Rehber rehber = new Rehber(id, name, phone);
                rehberList.add(rehber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rehberList;
    }

    public static void rehbereEkle(String name, String phone) {
        String sql = "INSERT INTO REHBER (isim,telefon)"+" VALUES('%s','%s');";
        sql=String.format(sql,name,phone);
        connectAndExecute(sql);
    }

    public static Rehber kisiGetir(int rehberId) {
        String sql = "SELECT * FROM REHBER WHERE ID = " + rehberId;

        try (
                Connection con= DriverManager.getConnection(conUrl,conUser,conPass);
                PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("isim");
                String phone = resultSet.getString("telefon");
                Rehber rehber = new Rehber(id, name, phone);
                return rehber;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Rehber> kisiGetir(String isim ) {

        List<Rehber> rehberList = new ArrayList<>();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String user = request.getParameter("isim");
        String sql = "SELECT * FROM REHBER WHERE ISIM ='"+isim+"'";

        try (
                Connection con= DriverManager.getConnection(conUrl,conUser,conPass);
                PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, isim);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("isim");
                String phone = resultSet.getString("telefon");
                Rehber rehber = new Rehber(id, name, phone);
                rehberList.add(rehber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rehberList;

//        List<Rehber> rehberList = new ArrayList<>();
//
//        String sql = "SELECT * FROM REHBER WHERE ISIM = ?";
//
//        try (
//                Connection con= DriverManager.getConnection(conUrl,conUser,conPass);
//                PreparedStatement preparedStatement = con.prepareStatement(sql);
//        ) {
//            preparedStatement.setString(1, isim);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt(1);
//                String name = resultSet.getString("isim");
//                String phone = resultSet.getString("telefon");
//                Rehber rehber = new Rehber(id, name, phone);
//                rehberList.add(rehber);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rehberList;
     }
    //static RehberManagedBean rehberManagedBean = new RehberManagedBean();
    public static void kisiSil(int id)
    {

//        try {
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            if (request.getParameterValues("isim")!=null)
//            {
//                for (String isim:request.getParameterValues("isim"))
//                {
//                    rehberManagedBean.delete.....;
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            this.error = e.getMessage();
//        }

        String sql = "DELETE FROM REHBER WHERE ID=%d";
        sql=String.format(sql,id);
        connectAndExecute(sql);
    }

    public static void kisiGuncelle(int id,String name,String phone){
        String sql = "UPDATE REHBER SET ISIM='%s',TELEFON='%s' WHERE ID=%d";
        sql=String.format(sql,name,phone,id);
        connectAndExecute(sql);
    }

    public static void classForName(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void connectAndExecute(String sql){
        try (Connection con= DriverManager.getConnection(
                conUrl,conUser,conPass);
             PreparedStatement preparedStatement = con.prepareStatement(sql);
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}