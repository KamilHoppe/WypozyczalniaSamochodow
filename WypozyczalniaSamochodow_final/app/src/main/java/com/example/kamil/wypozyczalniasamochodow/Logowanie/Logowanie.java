package com.example.kamil.wypozyczalniasamochodow.Logowanie;

/**
 * Created by Kamil on 18.12.2016.
 */

public class Logowanie {

    int idKlient;
    String login,haslo,email,uprawnienia;

    public void setId(int idKlient)

    {
        this.idKlient = idKlient;
    }

    public int getId()
    {
        return this.idKlient;
    }

    public void setLogin(String login)
    {
        this.login=login;
    }

    public String getLogin()
    {
        return this.login;
    }
    public void setHaslo(String haslo)
    {
        this.haslo=haslo;
    }

    public String getHaslo ()
    {
        return this.haslo;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getUprawnienia()
    {return this.uprawnienia;}
    public void setUprawnienia(String uprawnienia)
    {
        this.uprawnienia=uprawnienia;
    }

}
