package com.example.kamil.wypozyczalniasamochodow.ObslugaBazy;

/**
 * Created by Kamil on 03.01.2017.
 */

public class ObslugaBazyElementyGUI {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "bazaAuto.db";

    public static final String CREATE_TABLE_REZERWACJA = "CREATE TABLE Rezerwacja (idRezerwacja INTEGER PRIMARY KEY NOT NULL, " +
            "DataStart STRING,DataStop STRING,Status STRING,idKonto INTEGER,idMarka INTEGER,idModel INTEGER);";

    public static final String CREATE_TABLE_DANE_DO_REZERWACJI = "CREATE TABLE DaneDoRezerwacji (idKlient INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,Imie STRING, Nazwisko STRING,Ulica STRING,NrDomu STRING,Miejscowosc STRING,KodPocztowy STRING,idKonto INTEGER UNIQUE);";

    public static final String CREATE_TABLE_MODEL = "CREATE TABLE Model (idModel INTEGER PRIMARY KEY NOT NULL, NazwaModelu STRING,Rok STRING,Moc STRING,Pojemnosc STRING,Wyposazenie STRING, OpisModelu STRING,Cena STRING,idMarka INTEGER);";

    public static final String CREATE_TABLE_KLASA_AUTA = "CREATE TABLE Klasa_Auta (idKlasaAuta INTEGER PRIMARY KEY NOT NULL, KlasaAuta STRING);";

    public static final String CREATE_TABLE_MARKA = "CREATE TABLE Marka (idMarka INTEGER PRIMARY KEY NOT NULL, NazwaMarki STRING,idKlasaAuta INTEGER);";

    public static final String CREATE_TABLE_LOGOWANIE = "CREATE TABLE Logowanie (idKonto INTEGER PRIMARY KEY NOT NULL, " +
            "Nazwa STRING UNIQUE,Haslo STRING,Email STRING UNIQUE,Uprawnienia STRING);";


    ///Tabela Logowanie
    public static final String NazwaTabeliLogowanie = "Logowanie";
    public static final String idKontoTabelaLogowanie = "idKonto";
    public static final String NazwaPolaTabelaLogowanie = "Nazwa";
    public static final String HasloPolaTabelaLogowanie = "Haslo";
    public static final String EmailPolaTabelaLogowanie = "Email";
    public static final String UPRAWNIENIAPolaTabelaLogowanie = "Uprawnienia";
    public static final String idKlientTabelaLogowanie = "idKlient";

    ////////////////////////////KLASA_AUTA

    public static final String NazwaTabeliKlasaAuta ="Klasa_Auta";
    public static final String idKLasaAutaTabelaKlasaAuta = "idKlasaAuta";
    public static final String KlasaAutaNazwaPolaTabelaKlasaAuta ="KlasaAuta";

    /////////////////////////////MARKA
    public static final String NazwaTabeliMarka = "Marka";
    public static final String idMarkaTabelaMarka ="idMarka";
    public static final String NazwaMarkiNazwaPolaTabelaMarka = "NazwaMarki";
    public static final String idKlasaAutaTabelaMarka ="idKlasaAuta";


    /////////////////////////////MODEL
    public static final String NazwaTabeliModel ="Model";
    public static final String idModelTabelaModel ="idModel";
    public static final String NazwaModeluPolaTabelaModel = "NazwaModelu";
    public static final String RokNazwaPolaTabelaModel = "Rok";
    public static final String MocNazwaPolaTabelaModel = "Moc";
    public static final String PojemnoscNazwaPolaTabelaModel = "Pojemnosc";
   // public static final String WyposazenieNazwaPolaTabelaModel = "Wyposazenie";
    public static final String OpisModeluNazwaPolaTabelaModel = "OpisModelu";
    public static final String CenaNazwaPolaTabelaModel = "Cena";
    public static final String idMarkaTabelaModel = "idMarka";


    //////////////////////////////DaneDoRezerwacji
    public static final String NazwaTabeliDaneDoRezerwacji ="DaneDoRezerwacji";
    public static final String idKlientTabelaDaneDoRezerwacji ="idKlient";
    public static final String ImieTabelaDaneDoRezerwacji ="Imie";
    public static final String NazwiskoTabelaDaneDoRezerwacji ="Nazwisko";
    public static final String UlicaTabelaDaneDoRezerwacji ="Ulica";
    public static final String NrDomuTabelaDaneDoRezerwacji ="NrDomu";
    public static final String KodPocztowyTabelaDaneDoRezerwacji ="KodPocztowy";
    public static final String MiejscowoscTabelaDaneDoRezerwacji ="Miejscowosc";
    public static final String idKontoTabelaDaneDoRezerwacji ="idKonto";

    /////////////////////////////Rezerwacja
    public static final String NazwaTabeliRezerwacja ="Rezerwacja";
    public static final String idRezerwacjaTabelaRezerwacja ="idRezerwacja";
    public static final String DataStartTabelaRezerwacja ="DataStart";
    public static final String DataStopTabelaRezerwacja ="DataStop";
    public static final String StatusTabelaRezerwacja ="Status";
    public static final String idKontoTabelaRezerwacja ="idKonto";
//
    public static final String Status ="Do realizacji";

}
