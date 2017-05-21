package com.example.kamil.wypozyczalniasamochodow.MenuUzytkownika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamil.wypozyczalniasamochodow.MainActivity;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.DataBaseHelper;
import com.example.kamil.wypozyczalniasamochodow.ObslugaBazy.ObslugaBazyElementyGUI;
import com.example.kamil.wypozyczalniasamochodow.R;

public class MenuUzytkownik extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private boolean doubleBackToExitPressedOnce = false;
    Spinner klasaAuta,nazwaMarki,nazwaModelu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_uzytkownik);

        klasaAuta = (Spinner) findViewById(R.id.klasaAutaSpinner);
        nazwaMarki = (Spinner) findViewById(R.id.MarkaSpinner);
        nazwaModelu = (Spinner) findViewById(R.id.ModelSpinner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

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
            SharedPreferences sharedPreferences = getSharedPreferences("Plik", 0);
            sharedPreferences.edit().remove(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie).commit();
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SamochodyFragment samochodyFragment = new SamochodyFragment();

                    return samochodyFragment;
                case 1:
                    RezerwacjaFragment rezerwacjaFragment = new RezerwacjaFragment();
                    return rezerwacjaFragment;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Samochody";
                case 1:
                    DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
                    SharedPreferences prefs = getSharedPreferences("Plik", MODE_PRIVATE);
                    String pobierzLogin = prefs.getString(ObslugaBazyElementyGUI.NazwaPolaTabelaLogowanie, "");
                    Cursor c=helper.listaIdRezerwacji(pobierzLogin);
                    int liczba= c.getCount();
                    return "Rezerwacje ("+liczba+")";
            }
            return null;
        }
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Naciśnij ponownie WSTECZ aby zakończyć", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
