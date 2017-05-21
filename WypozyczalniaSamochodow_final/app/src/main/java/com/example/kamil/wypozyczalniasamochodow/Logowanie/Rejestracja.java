package com.example.kamil.wypozyczalniasamochodow.Logowanie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.MainActivity;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;

/**
 * Created by Kamil on 18.12.2016.
 */

public class Rejestracja extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.noActionBar);
        setContentView(R.layout.rejestracja);

    }

        public void buttonZarejestrujClick (View v)
        {
            if(v.getId() == R.id.buttonRejestruj) {

                EditText loginEditText = (EditText) findViewById(R.id.loginRejestracjaEditText);
                EditText hasloEditText = (EditText) findViewById(R.id.hasloRejestracjaEditText);
                EditText emailEditText = (EditText) findViewById(R.id.emailRejestracjaEditText);
                Spinner czyPracownikSpinner = (Spinner) findViewById(R.id.czyPracownikSpinner);
                String pobierzLogin = loginEditText.getText().toString();
                String pobierzHaslo = hasloEditText.getText().toString();
                String pobierzEmail = emailEditText.getText().toString();
                String czyPracownik = String.valueOf(czyPracownikSpinner.getSelectedItem());;

                Logowanie logowanie = new Logowanie();
                logowanie.setLogin(pobierzLogin);
                logowanie.setHaslo(pobierzHaslo);
                logowanie.setEmail(pobierzEmail);
                logowanie.setUprawnienia(czyPracownik);

                DataBaseHelper helper = new DataBaseHelper(this);


                if (pobierzHaslo.equals("") || pobierzLogin.equals("") || pobierzEmail.equals("") || czyPracownik.equals("")) {
                    Toast.makeText(Rejestracja.this, "Uzupełnij dane", Toast.LENGTH_LONG).show();
                }
               else if (helper.insertDaneDoRejestracji(logowanie) == true) { //jesli nie istnieja dane do rejestracji to zarejestruje,inaczej wyskoczy toast z else
                    Toast.makeText(Rejestracja.this, "Zarejestrowano pomyślnie.", Toast.LENGTH_LONG).show();
                    super.onBackPressed();
                }  else {
                    Toast.makeText(Rejestracja.this, "Konto lub email istnieje.", Toast.LENGTH_LONG).show();
                }


            }
            }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
        }





