package com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;
import com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta.DaneDoRezerwacji;

public class DaneAuta extends AppCompatActivity {


    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dane_auta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MenuUzytkownik.class);
                startActivity(i);
            }
        });
        DataBaseHelper helper = new DataBaseHelper(this);

        TextView wstawMarke = (TextView) findViewById(R.id.textViewWypiszMarke);
        TextView wstawModel = (TextView) findViewById(R.id.textViewWypiszModel);
        TextView wstawRok = (TextView) findViewById(R.id.textViewWypiszRok);
        TextView wstawPojemnosc = (TextView) findViewById(R.id.textViewWypiszPojemnosc);
        TextView wstawMoc = (TextView) findViewById(R.id.textViewWypiszMoc);
        TextView wstawWypozazenie = (TextView) findViewById(R.id.textViewWyposazenie);
        TextView wstawOpisModelu = (TextView) findViewById(R.id.textViewWypiszOpisModelu);
        TextView wstawCena = (TextView) findViewById(R.id.textViewWypiszCena);
        Button buttonPrzejdzDoRezerwacji = (Button) findViewById(R.id.buttonPrzejdzDoRezerwacji);

        Intent iin = getIntent(); //pobranie intentu
        Bundle b = iin.getExtras();
        if (b != null) {
            String pobierzModel = (String) b.get("Model");
            Cursor cursor = helper.wczytajDaneOAucie(getApplicationContext(), pobierzModel);
            if (cursor.getCount() > 0) {
                wstawMarke.setText(cursor.getString(0));
                wstawModel.setText(cursor.getString(1));
                wstawRok.setText(cursor.getString(2));
                wstawPojemnosc.setText(cursor.getString(3));
                wstawMoc.setText(cursor.getString(4));
                wstawWypozazenie.setText(cursor.getString(5));
                wstawOpisModelu.setText(cursor.getString(6));
                wstawCena.setText(cursor.getString(7));
            }
        }

    buttonPrzejdzDoRezerwacji.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(), DaneDoRezerwacji.class);
            startActivity(i);
        }
    });

    }
}