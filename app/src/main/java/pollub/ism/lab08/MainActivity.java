package pollub.ism.lab08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import pollub.ism.lab08.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ArrayAdapter<CharSequence> adapter;

    private String wybraneWarzywoNazwa = null;
    private Integer wybraneWarzywoIlosc = null;
    private String wybraneWarzywoHistoria = null;

    public enum OperacjaMagazynowa {SKLADUJ, WYDAJ};

    private BazaMagazynowa bazaDanych;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = ArrayAdapter.createFromResource(this, R.array.Asortyment, android.R.layout.simple_dropdown_item_1line);
        binding.spinner.setAdapter(adapter);

        binding.przyciskSkladuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zmienStan(OperacjaMagazynowa.SKLADUJ); // <---
                TextView textView=findViewById(R.id.tekstJednostka);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ',' HH:mm:ss z+2");
                String currentDateandTime = sdf.format(new Date());
                textView.setText(currentDateandTime);

            }
        });

        binding.przyciskWydaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zmienStan(OperacjaMagazynowa.WYDAJ); // <---
                TextView textView=findViewById(R.id.tekstJednostka);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ',' HH:mm:ss z+2");
                String currentDateandTime = sdf.format(new Date());
                textView.setText(currentDateandTime);

            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                wybraneWarzywoNazwa = adapter.getItem(i).toString(); // <---
                aktualizuj();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Nie będziemy implementować, ale musi być
            }
        });

        bazaDanych = Room.databaseBuilder(getApplicationContext(), BazaMagazynowa.class, BazaMagazynowa.NAZWA_BAZY)
                .allowMainThreadQueries().build();

        if(bazaDanych.pozycjaMagazynowaDAO().size() == 0){
            String[] asortyment = getResources().getStringArray(R.array.Asortyment);
            for(String nazwa : asortyment){
                PozycjaMagazynowa pozycjaMagazynowa = new PozycjaMagazynowa();
                pozycjaMagazynowa.NAME = nazwa; pozycjaMagazynowa.QUANTITY = 0; pozycjaMagazynowa.HISTORIA = "P";
                bazaDanych.pozycjaMagazynowaDAO().insert(pozycjaMagazynowa);
            }
        }



    }

    private void aktualizuj(){
        wybraneWarzywoIlosc = bazaDanych.pozycjaMagazynowaDAO().findQuantityByName(wybraneWarzywoNazwa);
        wybraneWarzywoHistoria = bazaDanych.pozycjaMagazynowaDAO().findQuantityByName2(wybraneWarzywoNazwa);
        binding.tekstStanMagazynu.setText("Stan magazynu dla " + wybraneWarzywoNazwa + " wynosi: " + wybraneWarzywoIlosc);

        TextView textView=findViewById(R.id.tekstJednostka);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ',' HH:mm:ss z+2");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        binding.textView.setText(wybraneWarzywoHistoria+"dff\n");
    }

    private void zmienStan(OperacjaMagazynowa operacja){

        Integer zmianaIlosci = null, nowaIlosc = null;
        String nowaHistoria = null;

        try {
            zmianaIlosci = Integer.parseInt(binding.edycjaIlosc.getText().toString());
        }catch(NumberFormatException ex){
            return;
        }finally {
            binding.edycjaIlosc.setText("");
        }

        TextView textView=findViewById(R.id.tekstJednostka);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd ',' HH:mm:ss z+2");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);

        switch (operacja){
            case SKLADUJ: {
                nowaIlosc = wybraneWarzywoIlosc + zmianaIlosci;
                nowaHistoria = wybraneWarzywoHistoria + currentDateandTime;
                break;
            }
            case WYDAJ: nowaIlosc = wybraneWarzywoIlosc - zmianaIlosci; nowaHistoria = wybraneWarzywoHistoria + currentDateandTime; break;
        }

        bazaDanych.pozycjaMagazynowaDAO().updateQuantityByName(wybraneWarzywoNazwa,nowaIlosc);
        bazaDanych.pozycjaMagazynowaDAO().updateQuantityByName2(wybraneWarzywoNazwa,nowaHistoria);

        aktualizuj();
    }
}