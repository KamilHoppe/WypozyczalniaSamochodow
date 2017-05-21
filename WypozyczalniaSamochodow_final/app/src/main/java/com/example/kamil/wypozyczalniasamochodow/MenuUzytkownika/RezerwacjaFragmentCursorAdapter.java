package com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;

/**
 * Created by Kamil on 10.01.2017.
 */

public class RezerwacjaFragmentCursorAdapter extends CursorAdapter {

        public RezerwacjaFragmentCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.rezerwacja_listaview, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView textViewMarka = (TextView) view.findViewById(R.id.markaTextView);
            TextView textViewModel = (TextView) view.findViewById(R.id.modelTextView);
            TextView textViewImie = (TextView) view.findViewById(R.id.ImieTextView);
            TextView textViewNazwisko = (TextView) view.findViewById(R.id.NazwiskoTextView);
            TextView textViewUlica = (TextView) view.findViewById(R.id.UlicaTextView);
            TextView textViewNrDomu = (TextView) view.findViewById(R.id.nrDomuTextView);
            TextView textViewKodPocztowy = (TextView) view.findViewById(R.id.kodPocztowyTextView);
            TextView textViewMiejscowosc = (TextView) view.findViewById(R.id.miejscowoscTextView);
            TextView textViewDataStart = (TextView) view.findViewById(R.id.DataStartTextView);
            TextView textViewDataStop = (TextView) view.findViewById(R.id.DataStopTextView);
            TextView textViewStatus = (TextView) view.findViewById(R.id.StatusTextView);
            TextView textViewCena = (TextView) view.findViewById(R.id.TextViewCenaLista);


            String nazwaMarki = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.NazwaMarkiNazwaPolaTabelaMarka));
            String nazwaModelu = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.NazwaModeluPolaTabelaModel));
            String imie = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.ImieTabelaDaneDoRezerwacji));
            String nazwisko = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.NazwiskoTabelaDaneDoRezerwacji));
            String ulica = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.UlicaTabelaDaneDoRezerwacji));
            String nrDomu = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.NrDomuTabelaDaneDoRezerwacji));
            String kodPocztowy = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.KodPocztowyTabelaDaneDoRezerwacji));
            String miejscowosc = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.MiejscowoscTabelaDaneDoRezerwacji));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.StatusTabelaRezerwacja));
            String dataStart = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.DataStartTabelaRezerwacja));
            String dataStop = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.DataStopTabelaRezerwacja));
            String cena = cursor.getString(cursor.getColumnIndexOrThrow(ObslugaBazyElementyGUI.CenaNazwaPolaTabelaModel));


            textViewMarka.setText(nazwaMarki);
            textViewModel.setText(nazwaModelu);
            textViewImie.setText(imie);
            textViewNazwisko.setText(nazwisko);
            textViewUlica.setText(ulica);
            textViewNrDomu.setText(nrDomu);
            textViewKodPocztowy.setText(kodPocztowy);
            textViewMiejscowosc.setText(miejscowosc);
            textViewStatus.setText(status);
            textViewDataStart.setText(dataStart);
            textViewDataStop.setText(dataStop);
            textViewCena.setText(cena);


        }

}
