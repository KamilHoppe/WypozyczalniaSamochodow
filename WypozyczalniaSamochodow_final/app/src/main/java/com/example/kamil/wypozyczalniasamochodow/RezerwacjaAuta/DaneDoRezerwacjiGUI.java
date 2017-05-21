package com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta;

import android.provider.ContactsContract;

/**
 * Created by Kamil on 04.01.2017.
 */

public class DaneDoRezerwacjiGUI {

    String Imie, Nazwisko, Ulica, NrDomu, KodPocztowy, Miejscowosc;

    public String getImie() {
        return this.Imie;
    }

    public void setImie(String Imie)
    {
        this.Imie=Imie;
    }
    public String getNazwisko() {
        return this.Nazwisko;
    }

    public void setNazwisko(String Nazwisko)
    {
        this.Nazwisko=Nazwisko;
    }
    public String getUlica() {
        return this.Ulica;
    }

    public void setUlica(String Ulica)
    {
        this.Ulica=Ulica;
    }

    public String getNrDomu() {
        return this.NrDomu;
    }

    public void setNrDomu(String NrDomu)
    {
        this.NrDomu=NrDomu;
    }
    public String getKodPocztowy() {
        return this.KodPocztowy;
    }

    public void setKodPocztowy(String KodPocztowy)
    {
        this.KodPocztowy=KodPocztowy;
    }
    public String getMiejscowosc() {
        return this.Miejscowosc;
    }

    public void setMiejscowosc(String Miejscowosc)
    {
        this.Miejscowosc=Miejscowosc;
    }
    
}
