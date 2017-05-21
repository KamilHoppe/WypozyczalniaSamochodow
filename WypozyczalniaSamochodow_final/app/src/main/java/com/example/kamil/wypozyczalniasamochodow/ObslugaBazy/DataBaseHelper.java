package com.example.kamil.wypozyczalniasamochodow.ObslugaBazy;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.kamil.wypozyczalniasamochodow.Logowanie.Logowanie;
import com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta.DaneDoRezerwacji;
import com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta.DaneDoRezerwacjiGUI;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 03.01.2017.
 */

public class DataBaseHelper extends SQLiteAssetHelper {

    SQLiteDatabase baza;
    boolean warunekRejestracji;



    public DataBaseHelper(Context context) {
        super(context, ObslugaBazyElementyGUI.DATABASE_NAME, null, ObslugaBazyElementyGUI.DATABASE_VERSION);
    }


 /*   @Override
  public void onCreate(SQLiteDatabase db) {
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_LOGOWANIE);
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_KLASA_AUTA);
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_MARKA);
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_MODEL);
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_DANE_DO_REZERWACJI);
      db.execSQL(ObslugaBazyElementyGUI.CREATE_TABLE_REZERWACJA);
      this.baza=db;

  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliLogowanie);
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliKlasaAuta);
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliMarka);
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliModel);
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliDaneDoRezerwacji);
      db.execSQL("DROP TABLE IF EXISTS" + ObslugaBazyElementyGUI.NazwaTabeliRezerwacja);
      this.onCreate(baza);

  } */
    //metoda dodajaca dane do rejestracji
    public boolean insertDaneDoRejestracji(Logowanie logowanie) {
        baza = this.getWritableDatabase();
        ContentValues vaules = new ContentValues();
        String query = "SELECT * FROM " + ObslugaBazyElementyGUI.NazwaTabeliLogowanie;
        Cursor cursor = baza.rawQuery(query, null);
        int count = cursor.getCount();
        vaules.put(ObslugaBazyElementyGUI.idKontoTabelaLogowanie, count);
        vaules.put(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie, logowanie.getLogin());
        vaules.put(ObslugaBazyElementyGUI.HasloPolaTabelaLogowanie, logowanie.getHaslo());
        vaules.put(ObslugaBazyElementyGUI.EmailPolaTabelaLogowanie, logowanie.getEmail());
        vaules.put(ObslugaBazyElementyGUI.UPRAWNIENIAPolaTabelaLogowanie,logowanie.getUprawnienia());

        try {
            warunekRejestracji=true;
            baza.insertOrThrow(ObslugaBazyElementyGUI.NazwaTabeliLogowanie, null, vaules);
            baza.close();
        } catch (SQLiteConstraintException e) {
            warunekRejestracji=false;
        }
        return warunekRejestracji;
    }

    //sprawdza czy konto istnieje w bazie po loginie
    public String searchPass(String nazwa)
    {
        baza = this.getReadableDatabase();
        String query = "SELECT NAZWA,HASLO FROM "+ ObslugaBazyElementyGUI.NazwaTabeliLogowanie;
        Cursor cursor = baza.rawQuery(query,null);
        String nazwaPola,hasloPola;
        hasloPola = "Nie znaleziono";
        if(cursor.moveToFirst())
        {
            do{
                nazwaPola = cursor.getString(0);
                if(nazwa.equals(nazwaPola))
                {
                    hasloPola = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return hasloPola;
    }

    //szuka pracownika w bazie w zapytaniu where
    public boolean searchWorker (String nazwa) {
        baza = this.getReadableDatabase();
        String query = "SELECT Nazwa,Haslo FROM " + ObslugaBazyElementyGUI.NazwaTabeliLogowanie + " WHERE Uprawnienia='Pracownik';";
        Cursor cursor = baza.rawQuery(query, null);
        String nazwaPola;

        if (cursor.moveToFirst()) {
            do {
                nazwaPola = cursor.getString(0);
                if (nazwa.equals(nazwaPola)) {

                    return true;
                }
            }
            while (cursor.moveToNext());
        }
        return false;
    }

    //"SELECT Marka.NazwaMarki from Klasa_Auta,Marka WHERE Klasa_Auta.idKlasaAuta=Marka.idKlasaAuta"
//select KLASA_AUTA.klasaauta,MARKA.nazwamarki,MODEL.nazwa from klasa_auta,marka,model
// where klasa_auta.idKlasaAuta=marka.idKlasaAuta and marka.idMarka=model.idModel;
//wczytanie dla spinnera klasy auta

    public List<String> wczytajklaseAutaLista(Context context) {
        baza = context.openOrCreateDatabase(ObslugaBazyElementyGUI.DATABASE_NAME,SQLiteDatabase.OPEN_READWRITE,null);
        String query = "SELECT KlasaAuta from "+ObslugaBazyElementyGUI.NazwaTabeliKlasaAuta;
        Cursor cursor = baza.rawQuery(query,null);
        List<String> listaWynikuZapytania = new ArrayList<String>();
        int dlKursora;
        dlKursora = cursor.getCount();
        int i = 0;
        cursor.moveToFirst();
        while(i < dlKursora) {
            listaWynikuZapytania.add(cursor.getString(0));
            cursor.moveToNext();
            i++;
        }
        baza.close();
        return listaWynikuZapytania;
    }
//wczytanie dla spinnera marki auta
    public List<String> wczytajMarkeAutaLista(Context context,String klasa) {
        baza = context.openOrCreateDatabase(ObslugaBazyElementyGUI.DATABASE_NAME,SQLiteDatabase.OPEN_READWRITE,null);
        String query = "SELECT Marka.NazwaMarki from Klasa_Auta,Marka WHERE Klasa_Auta.idKlasaAuta=Marka.idKlasaAuta and Klasa_Auta.KlasaAuta='"+klasa+"'";
        Cursor cursor = baza.rawQuery(query,null);
        List<String> listaWynikuZapytania = new ArrayList<String>();
        int dlKursora;
        dlKursora = cursor.getCount();
        int i = 0;
        cursor.moveToFirst();
        while(i < dlKursora) {
            listaWynikuZapytania.add(cursor.getString(0));
            cursor.moveToNext();
            i++;
        }
        baza.close();
        return listaWynikuZapytania;
    }
    //wczytanie dla spinnera modelu auta
    public List<String> wczytajModelAutaLista(Context context,String marka) {
        baza = context.openOrCreateDatabase(ObslugaBazyElementyGUI.DATABASE_NAME,SQLiteDatabase.OPEN_READWRITE,null);
        String query = "SELECT Model.NazwaModelu from Marka,Model WHERE Marka.idMarka=Model.idMarka and Marka.NazwaMarki='"+marka+"'";
        Cursor cursor = baza.rawQuery(query,null);
        List<String> listaWynikuZapytania = new ArrayList<String>();
        int dlKursora;
        dlKursora = cursor.getCount();
        int i = 0;
        cursor.moveToFirst();
        while(i < dlKursora) {
            listaWynikuZapytania.add(cursor.getString(0));
            cursor.moveToNext();
            i++;
        }
        baza.close();
        return listaWynikuZapytania;
    }
//marka,model,rok,pojemnosc,moc,opismodelu,cena
    //select Marka.NazwaMarki, Model.Nazwa,Model.Rok,Model.Pojemnosc,Model.OpisModelu,Model.Cena from Marka,Model Where Marka.idMarka=Model.idMarka and Model.Nazwa='Astra';
    public Cursor wczytajDaneOAucie(Context context,String pobierzWartosc) {
        SQLiteDatabase baza = context.openOrCreateDatabase(ObslugaBazyElementyGUI.DATABASE_NAME, SQLiteDatabase.OPEN_READWRITE, null);
        String SELECT_QUERY = "select Marka.NazwaMarki, Model.NazwaModelu,Model.Rok,Model.Pojemnosc,Model.Moc,Model.Wyposazenie,"+
        "Model.OpisModelu,Model.Cena from Marka,Model Where Marka.idMarka=Model.idMarka and Model.NazwaModelu='"+pobierzWartosc+"';";
        Cursor k =baza.rawQuery(SELECT_QUERY,null);
        k.moveToFirst();
        baza.close();
        return k;
    }


    public void insertDaneDoRezerwacji(DaneDoRezerwacjiGUI daneDoRezerwacjiGUI,String pobierzLogin) {
        baza = this.getWritableDatabase();

        String query = "INSERT OR REPLACE INTO DaneDoRezerwacji "+
        "(Imie,Nazwisko,Ulica,NrDomu,KodPocztowy,Miejscowosc,idKonto)"+
                "VALUES('"+daneDoRezerwacjiGUI.getImie()+"','"+daneDoRezerwacjiGUI.getNazwisko()+
                "','"+daneDoRezerwacjiGUI.getUlica()+"','"+daneDoRezerwacjiGUI.getNrDomu()+
                "','"+daneDoRezerwacjiGUI.getKodPocztowy()+"','"+daneDoRezerwacjiGUI.getMiejscowosc()+
                "',(SELECT idKonto from Logowanie WHERE Nazwa='"+pobierzLogin+"'))";

            baza.execSQL(query);
            baza.close();

    }

    public Cursor wczytajDaneORezerwacji(Context context,String pobierzWartosc) {
        SQLiteDatabase baza = context.openOrCreateDatabase(ObslugaBazyElementyGUI.DATABASE_NAME, SQLiteDatabase.OPEN_READWRITE, null);
        String SELECT_QUERY = "SELECT DaneDoRezerwacji.Imie,DaneDoRezerwacji.Nazwisko,"+
        "DaneDoRezerwacji.Ulica,DaneDoRezerwacji.NrDomu,DaneDoRezerwacji.KodPocztowy,"+
                "DaneDoRezerwacji.Miejscowosc FROM DaneDoRezerwacji,Logowanie Where "+
                "Logowanie.Nazwa='"+pobierzWartosc+"' and Logowanie.idKonto=DaneDoRezerwacji.idKonto";
        Cursor k =baza.rawQuery(SELECT_QUERY,null);
        k.moveToFirst();
        baza.close();
        return k;
    }
    public boolean SprawdzenieCzyRekordIstnieje(String pobierzWartosc) {
        baza = this.getReadableDatabase();
        String Query = "SELECT DaneDoRezerwacji.idKlient,DaneDoRezerwacji.Imie,"+
        "DaneDoRezerwacji.Nazwisko,DaneDoRezerwacji.Ulica,DaneDoRezerwacji.NrDomu,DaneDoRezerwacji.KodPocztowy,"+
                "DaneDoRezerwacji.Miejscowosc FROM DaneDoRezerwacji,Logowanie Where "+
                "Logowanie.Nazwa='"+pobierzWartosc+"' and Logowanie.idKonto=DaneDoRezerwacji.idKonto";
        Cursor cursor = baza.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public void insertRezerwacja(String pobierzLogin,String pobierzDataStart,String pobierzDataStop,String pobierzModel,String pobierzMarke) {
        baza = this.getWritableDatabase();
        String query = "SELECT * FROM "+ ObslugaBazyElementyGUI.NazwaTabeliRezerwacja;
        Cursor cursor = baza.rawQuery(query, null);
        int count = cursor.getCount();

        String query2 = "INSERT INTO Rezerwacja (idRezerwacja,DataStart,DataStop,Status,idKonto,idMarka,idModel) VALUES("+count+
                ",'"+pobierzDataStart+"','"+pobierzDataStop+"','"+ObslugaBazyElementyGUI.Status+
                "',(SELECT idKonto from LOGOWANIE WHERE Nazwa='"+pobierzLogin+"'),(SELECT idMarka from Marka WHERE NazwaMarki='"+
                pobierzMarke+"'),(SELECT idModel from Model WHERE NazwaModelu='"+pobierzModel+"'))";

        baza.execSQL(query2);
        baza.close();

            }

    public Cursor listaIdRezerwacji(String pobierzLogin) {
        baza = this.getReadableDatabase();
        final String SQL_STATEMENT = "SELECT Rezerwacja.idRezerwacja FROM Rezerwacja,Logowanie,Model,Marka "+
                "WHERE Logowanie.Nazwa='"+pobierzLogin+"' and Rezerwacja.idKonto=Logowanie.idKonto and "+
                "Rezerwacja.idMarka=Marka.idMarka and Rezerwacja.idModel=Model.idModel";
        Cursor c = baza.rawQuery(SQL_STATEMENT,null);
        c.moveToFirst();
        baza.close();
        return c;
    }

    public void updateStatus(String pobierzStatus,int pobierzIdRezerwacja) {
        baza = this.getWritableDatabase();
        String query2 = "UPDATE OR REPLACE Rezerwacja SET Status='"+pobierzStatus+"' Where idRezerwacja="+pobierzIdRezerwacja;
        baza.execSQL(query2);
        baza.close();

    }

}



