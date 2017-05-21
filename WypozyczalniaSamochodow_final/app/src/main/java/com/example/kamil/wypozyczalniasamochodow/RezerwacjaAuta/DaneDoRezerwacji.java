package com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika.DaneAuta;
import com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika.MenuUzytkownik;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DaneDoRezerwacji extends AppCompatActivity {

    SharedPreferences prefs;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar1 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dane_do_rezerwacji);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


           final EditText editTextDataStart=(EditText)findViewById(R.id.editTextDataStart);
           final EditText editTextDataStop=(EditText)findViewById(R.id.editTextDataStop);
          final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
           private void updateLabel() {
               String myFormat = "dd.MM.yy"; //In which you need put here
               SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
               editTextDataStart.setText(sdf.format(myCalendar.getTime()));
               final String pobierzDataStart = editTextDataStart.getText().toString();
               prefs = getSharedPreferences("Plik", Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = prefs.edit();
               editor.putString(ObslugaBazyElementyGUI.DataStartTabelaRezerwacja,pobierzDataStart);
               editor.commit();

           }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            private void updateLabel1() {
                String myFormat = "dd.MM.yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextDataStop.setText(sdf.format(myCalendar1.getTime()));
                final String pobierzDataStop = editTextDataStop.getText().toString();
                prefs = getSharedPreferences("Plik", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ObslugaBazyElementyGUI.DataStopTabelaRezerwacja,pobierzDataStop);
                editor.commit();
            }

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        editTextDataStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DaneDoRezerwacji.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextDataStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DaneDoRezerwacji.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        final EditText imieEditText = (EditText) findViewById(R.id.editTextImie);
        final EditText nazwiskoEditText = (EditText) findViewById(R.id.editTextNazwisko);
        final EditText ulicaEditText = (EditText) findViewById(R.id.editTextUlica);
        final EditText nrDomuEditText = (EditText) findViewById(R.id.editTextNrDomu);
        final EditText kodPocztowyEditText = (EditText) findViewById(R.id.editTextKodPocztowy);
        final EditText miejscowoscEditText = (EditText) findViewById(R.id.editTextMiejscowosc);

        final DataBaseHelper helper = new DataBaseHelper(this);

        prefs = this.getSharedPreferences("Plik", MODE_PRIVATE);
        final String pobierzLogin = prefs.getString(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie, "");



            if(helper.SprawdzenieCzyRekordIstnieje(pobierzLogin)==true) {
                Cursor cursor = helper.wczytajDaneORezerwacji(getApplicationContext(), pobierzLogin);
                if (cursor.getCount() > 0) {
                    try {

                        imieEditText.setText(cursor.getString(0));
                        nazwiskoEditText.setText(cursor.getString(1));
                        ulicaEditText.setText(cursor.getString(2));
                        nrDomuEditText.setText(cursor.getString(3));
                        kodPocztowyEditText.setText(cursor.getString(4));
                        miejscowoscEditText.setText(cursor.getString(5));
                    } catch (SQLiteConstraintException e) {
                    }

                }
            }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DaneAuta.class);
                startActivity(i);

            }
        });

        Button buttonRezerwacja = (Button) findViewById(R.id.buttonRezerwuje);


        buttonRezerwacja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String pobierzImie = imieEditText.getText().toString();
                String pobierzNazwisko = nazwiskoEditText.getText().toString();
                String pobierzUlica = ulicaEditText.getText().toString();
                String pobierzNrDomu = nrDomuEditText.getText().toString();
                String pobierzkodPocztowy = kodPocztowyEditText.getText().toString();
                String pobierzMiejscowosc = miejscowoscEditText.getText().toString();

                DaneDoRezerwacjiGUI daneDoRezerwacjiGUI = new DaneDoRezerwacjiGUI();
                daneDoRezerwacjiGUI.setImie(pobierzImie);
                daneDoRezerwacjiGUI.setNazwisko(pobierzNazwisko);
                daneDoRezerwacjiGUI.setUlica(pobierzUlica);
                daneDoRezerwacjiGUI.setNrDomu(pobierzNrDomu);
                daneDoRezerwacjiGUI.setKodPocztowy(pobierzkodPocztowy);
                daneDoRezerwacjiGUI.setMiejscowosc(pobierzMiejscowosc);




                prefs = getSharedPreferences("Plik", MODE_PRIVATE);
                String pobierzDataStart = prefs.getString(ObslugaBazyElementyGUI.DataStartTabelaRezerwacja, "");
                String pobierzDataStop = prefs.getString(ObslugaBazyElementyGUI.DataStopTabelaRezerwacja, "");
                String pobierzMarke = prefs.getString(ObslugaBazyElementyGUI.NazwaMarkiNazwaPolaTabelaMarka, "");
                String pobierzModel = prefs.getString(ObslugaBazyElementyGUI.NazwaModeluPolaTabelaModel, "");
                helper.insertDaneDoRezerwacji(daneDoRezerwacjiGUI, pobierzLogin);
                helper.insertRezerwacja(pobierzLogin,pobierzDataStart,pobierzDataStop,pobierzModel,pobierzMarke);
                Toast.makeText(DaneDoRezerwacji.this, "Dodano do rezerwacji.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MenuUzytkownik.class);
                startActivity(i);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(ObslugaBazyElementyGUI.DataStopTabelaRezerwacja,"");
                editor.putString(ObslugaBazyElementyGUI.DataStartTabelaRezerwacja,"");
                editor.commit();




            }
        });

    }



}
