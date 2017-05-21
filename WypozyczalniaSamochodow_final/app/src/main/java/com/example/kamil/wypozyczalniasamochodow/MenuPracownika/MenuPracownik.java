package com.example.kamil.wypozyczalniasamochodow.MenuPracownika;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.MainActivity;
import com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika.RezerwacjaFragmentCursorAdapter;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;
import com.example.kamil.wypozyczalniasamochodow.RezerwacjaAuta.DaneDoRezerwacji;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.kamil.wypozyczalniasamochodow.R.styleable.View;

public class MenuPracownik extends AppCompatActivity {
    ListView listView;
    Cursor todoCursor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pracownik);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    final   DataBaseHelper helper = new DataBaseHelper(this);

        listView = (ListView) findViewById(R.id.listaPracownika);

        prefs = getSharedPreferences("Plik", MODE_PRIVATE);
        String pobierzLogin = prefs.getString(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie, "");
        getSupportActionBar().setTitle("Witaj, "+pobierzLogin);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Query for items from the database and get a cursor back
        todoCursor = db.rawQuery("SELECT Rezerwacja.idRezerwacja AS _id, Marka.NazwaMarki,Model.NazwaModelu,DaneDoRezerwacji.Imie,DaneDoRezerwacji.Nazwisko,DaneDoRezerwacji.Ulica,DaneDoRezerwacji.NrDomu,DaneDoRezerwacji.KodPocztowy,DaneDoRezerwacji.Miejscowosc,Rezerwacja.Status,Rezerwacja.DataStart,Rezerwacja.DataStop,Model.Cena from Marka,Model,Rezerwacja,DaneDoRezerwacji,Logowanie where Rezerwacja.idMarka= Marka.idMarka AND Rezerwacja.idModel=Model.idModel AND Rezerwacja.idKonto=Logowanie.idKonto and DaneDoRezerwacji.idKonto=Rezerwacja.idKonto",null);
        // Setup cursor adapter using cursor from last step
        RezerwacjaFragmentCursorAdapter todoAdapter = new RezerwacjaFragmentCursorAdapter(this,todoCursor);

        listView.setAdapter(todoAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               final int pozycjaListy= parent.getPositionForView(view);

                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPracownik.this); //tworzenie alerdialogu

                builder.setTitle("Zmień status rezerwacji.");

                final String[] listaStatus = new String[]{
                        "Potwierdzono rezerwację",
                        "Wybierz inne auto",
                        "Wybierz inny termin",
                        "Rezerwacja anulowana",
                        "Rezerwacja rozpatrzona negatywnie"
                };

                // Set a single choice items list for alert dialog
                builder.setSingleChoiceItems(
                        listaStatus, // Items list
                        -1, // Index of checked item (-1 = no selection)
                        new DialogInterface.OnClickListener() // Item click listener
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the alert dialog selected item's text
                              String selectedItem = Arrays.asList(listaStatus).get(i);

                                helper.updateStatus(selectedItem,pozycjaListy); // update pola Status w bazie danych na ten wybrany z listy.

                            }
                        });

                // Set the a;ert dialog positive button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent ii = new Intent(getApplicationContext(), MenuPracownik.class); //odwieżanie strony
                        startActivity(ii);

                    }
                });

                // Create the alert dialog
                AlertDialog dialog = builder.create();
                // Finally, display the alert dialog
                dialog.show();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_wyloguj) {

            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            helper.close();
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("Plik", 0); //czyszczenie sharedpreferences i wyjdzie do mainactivity
            sharedPreferences.edit().remove(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie).commit();
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}
