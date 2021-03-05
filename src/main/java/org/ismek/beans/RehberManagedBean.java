package org.ismek.beans;

import org.ismek.db.DbOperations;
import org.ismek.domain.Rehber;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Facelet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "rehberMB")
public class RehberManagedBean {

    private int id;
    private String isim;
    private String telefon;
    private List<Rehber> rehberList = new ArrayList<>();
    private String error="";

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public RehberManagedBean() {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String rehberId = req.getParameter("rehberId");
        if (rehberId != null) {
            Rehber rehber = DbOperations.kisiGetir(Integer.valueOf(rehberId));
            id = rehber.getId();
            isim = rehber.getIsim();
            telefon = rehber.getTelefon();
        }
    }

    public void save() {
        DbOperations.rehbereEkle(isim, telefon);
        sendRedirect("/faces/index.xhtml");
    }

    public void edit(int id) {
        sendRedirect("/faces/edit.xhtml?rehberId=" + id);
    }

    public void delete(int id) {
        sendRedirect("/faces/delete.xhtml?rehberId=" + id);
    }

    private void sendRedirect(String url) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        ExternalContext externalContext = currentInstance.getExternalContext();
        try {
            externalContext.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        DbOperations.kisiGuncelle(id, isim, telefon);
        sendRedirect("/faces/index.xhtml");
    }

    public void delete() {
        DbOperations.kisiSil(id);
        sendRedirect("/faces/index.xhtml");
    }

    public void ara() throws ClassNotFoundException {

        List<Rehber> rehberListesi = DbOperations.kisiGetir(isim);
        for (Rehber rehber : rehberListesi) {
            System.out.println(rehber);
        }
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/deneme?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey","root","12345");
//            Statement statement = con.createStatement();
//            String query = "select * from Rehber where isim='"+isim+ "'";
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next())
//            {
//                resultSet.getInt("id");
//                resultSet.getString("isim");
//                resultSet.getString("telefon");
//            }
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }

    }

    public List<Rehber> getRehberList() {
        rehberList = DbOperations.tumRehberiGetir();
        return rehberList;
    }

    public void setRehberList(List<Rehber> rehberList) {
        this.rehberList = rehberList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}