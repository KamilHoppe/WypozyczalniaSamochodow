package com.example.kamil.wypozyczalniasamochodow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.Logowanie.Rejestracja;
import com.example.kamil.wypozyczalniasamochodow.MenuPracownika.MenuPracownik;
import com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika.MenuUzytkownik;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.noActionBar);
        setContentView(R.layout.activity_main);

    }

    public void buttonZalogujClick (View v) {
        if (v.getId() == R.id.buttonZaloguj) {
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());

            EditText loginEditText = (EditText) findViewById(R.id.loginEditText);
            String login = loginEditText.getText().toString();
            EditText hasloEditText = (EditText) findViewById(R.id.hasloEditText);
            String haslo = hasloEditText.getText().toString();

            sharedpreferences = getSharedPreferences("Plik", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie,login);
            editor.commit();


            String wartoscHasla= helper.searchPass(login); //zwraca haslo jesli istnieje nazwa

                if (wartoscHasla.equals(haslo) && helper.searchWorker(login) == false) {
                    Intent i = new Intent(getApplicationContext(), MenuUzytkownik.class);
                    startActivity(i);
                    // Toast.makeText(getApplicationContext(), "Nazwa lub hasło nieprawidłowe", Toast.LENGTH_LONG).show();
                }
            else if (helper.searchWorker(login) == true && wartoscHasla.equals(haslo)) {
                Intent i = new Intent(getApplicationContext(), MenuPracownik.class);
                startActivity(i);
            }

            else if (login.equals("") || haslo.equals("")) {
                    Toast.makeText(MainActivity.this, "Uzupełnij dane", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Nazwa użytkownika lub hasło nieprawidłowe.", Toast.LENGTH_LONG).show();
                }

        }
    }
    public void buttonRejestracjaClick (View v)
    {
        if(v.getId()== R.id.buttonRejestracja)

        {
            Intent i = new Intent(getApplicationContext(), Rejestracja.class);
            startActivity(i);

        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
