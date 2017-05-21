package com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;

import java.util.ArrayList;
import java.util.List;


public class  SamochodyFragment extends Fragment {

    Spinner klasaAuta,nazwaMarki,nazwaModelu;

    ImageView button;
    SharedPreferences sharedpreferences;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.samochody_menu_uzytkownik, container, false);
        klasaAuta = (Spinner) rootView.findViewById(R.id.klasaAutaSpinner);
        nazwaMarki = (Spinner) rootView.findViewById(R.id.MarkaSpinner);
        nazwaModelu = (Spinner) rootView.findViewById(R.id.ModelSpinner);
        button= (ImageView) rootView.findViewById(R.id.buttonClickMenuUzyt);

        sharedpreferences = getActivity().getSharedPreferences("Plik", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        List<String> listaDanychKlasaAuta = new ArrayList<String>();
        final DataBaseHelper dataBaseHelperWypozyczenie = new DataBaseHelper(getContext());

        listaDanychKlasaAuta = dataBaseHelperWypozyczenie.wczytajklaseAutaLista(getContext());

        ArrayAdapter<String> adapterKlasaAuta = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaDanychKlasaAuta);

        klasaAuta.setAdapter(adapterKlasaAuta);

        klasaAuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String klasaAutapobierz =  arg0.getItemAtPosition(arg2).toString();

                if(arg0.getId()==R.id.klasaAutaSpinner)
                {   List<String> listaDanychNazwaMarki = new ArrayList<String>();
                    listaDanychNazwaMarki = dataBaseHelperWypozyczenie.wczytajMarkeAutaLista(getContext(),klasaAutapobierz);
                    ArrayAdapter<String> adapterNazwaMarki = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaDanychNazwaMarki);
                    nazwaMarki.setAdapter(adapterNazwaMarki);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        nazwaMarki.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                final String markaAutapobierz =  arg0.getItemAtPosition(arg2).toString();

                if(arg0.getId()==R.id.MarkaSpinner)
                {     List<String> listaDanychNazwaModelu = new ArrayList<String>();
                    listaDanychNazwaModelu = dataBaseHelperWypozyczenie.wczytajModelAutaLista(getContext(),markaAutapobierz);

                    ArrayAdapter<String> adapterNazwaModelu = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaDanychNazwaModelu);
                    nazwaModelu.setAdapter(adapterNazwaModelu);

                    editor.putString(ObslugaBazyElementyGUI.NazwaMarkiNazwaPolaTabelaMarka,markaAutapobierz);
                    editor.apply();




                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    nazwaModelu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                   int arg2, long arg3) {
            final String pobierzNazweModelu = nazwaModelu.getSelectedItem().toString();
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    editor.putString(ObslugaBazyElementyGUI.NazwaModeluPolaTabelaModel,pobierzNazweModelu);
                    editor.commit();
                    startActivity(new Intent(getActivity(), DaneAuta.class).putExtra("Model", pobierzNazweModelu));

                }
            });


             }
                @Override
                public void onNothingSelected (AdapterView < ? > arg0) {
                    // TODO Auto-generated method stub
                }

        });



        return rootView;
    }

}