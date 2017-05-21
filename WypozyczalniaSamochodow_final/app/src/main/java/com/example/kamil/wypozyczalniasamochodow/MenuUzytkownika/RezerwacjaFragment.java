package com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.MenuPracownika.MenuPracownik;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;
import com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika.MenuUzytkownik;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class  RezerwacjaFragment extends Fragment {

    ListView listView;
    SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rezerwacja_menu_uzytkownik, container, false);

        listView = (ListView)rootView.findViewById(R.id.rezerwacjaListView);
        prefs = getContext().getSharedPreferences("Plik", MODE_PRIVATE);
        String pobierzLogin = prefs.getString(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie, "");


        DataBaseHelper helper = new DataBaseHelper(getContext());
        SQLiteDatabase db = helper.getWritableDatabase();


        Cursor todoCursor = db.rawQuery("SELECT Rezerwacja.idRezerwacja AS _id, Marka.NazwaMarki,Model.NazwaModelu,"+
                "DaneDoRezerwacji.Imie,DaneDoRezerwacji.Nazwisko,DaneDoRezerwacji.Ulica,DaneDoRezerwacji.NrDomu,"+
                "DaneDoRezerwacji.KodPocztowy,DaneDoRezerwacji.Miejscowosc,Rezerwacja.Status,Rezerwacja.DataStart,"+
                "Rezerwacja.DataStop, Model.Cena from Marka,Model,Rezerwacja,DaneDoRezerwacji,Logowanie "+
                "where Rezerwacja.idMarka= Marka.idMarka AND Rezerwacja.idModel=Model.idModel AND "+
                "Rezerwacja.idKonto=Logowanie.idKonto and DaneDoRezerwacji.idKonto=Rezerwacja.idKonto "+
                "and Logowanie.Nazwa='"+pobierzLogin+"'", null);

        RezerwacjaFragmentCursorAdapter todoAdapter = new RezerwacjaFragmentCursorAdapter(getContext(),todoCursor);

        listView.setAdapter(todoAdapter);

        return rootView;
    }

}