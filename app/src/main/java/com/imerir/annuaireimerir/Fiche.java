package com.imerir.annuaireimerir;

import java.net.URL;

/**
 * Created by Axel Zapata on 09/01/2017.
 * For Imerir.
 * axel.zapata@imerir.com
 */
public class Fiche {

    private Integer id;
    private String mail;
    private String phone;
    private URL picture;

    public Fiche(Integer id, String mail, String phone, URL picture) {
        this.id = id;
        this.mail = mail;
        this.phone = phone;
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }
}
