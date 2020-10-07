package com.example.databaser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.databaser.DBHandler.KEY_PH_NO;
import static com.example.databaser.DBHandler.TABLE_KONTAKTER;

public class MainActivity extends AppCompatActivity {

    EditText navninn, telefoninn, idinn;
    TextView utskrift;
    DBHandler db;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navninn = (EditText) findViewById(R.id.navn);
        telefoninn = (EditText) findViewById(R.id.nummer);
        //idinn = (EditText) findViewById(R.id.min_id);
       // utskrift = (TextView) findViewById(R.id.utskrift);
        db = new DBHandler(this);
        lv = (ListView)findViewById(R.id.liste);
       List<Kontakt> liste = db.finnAlleKontakter();
        final ArrayAdapter<Kontakt> adapter = new ArrayAdapter<Kontakt>(this,android.R.layout.simple_list_item_1,liste);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapter.getItem(i);
                System.out.println("du trakk på "+data.getNavn() + " tl: " + data.getTelefon());
            }
        });

    }
    public void leggtil(View V){
        Kontakt kontakt= new Kontakt(navninn.getText().toString(), telefoninn.getText().toString());
        db.leggTilKontakter(kontakt);
        Log.d("Legg inn: ", "legger til kontakter");
        recreate();
    }
    public ArrayList<String> visAlle(){
        String tekst = "";
        List<Kontakt> kontakter = db.finnAlleKontakter();
        ArrayList<String> navn_Og_tlf = new ArrayList<>();
        for (Kontakt kontakt: kontakter) {
            navn_Og_tlf.add(kontakt.navn+"\n"+kontakt.getTelefon())
        }
        return navn_Og_tlf;
    }
    public void slett(View V){
       Long kontaktid = (Long.parseLong(telefoninn.getText().toString()));
       db.slettKontakt(kontaktid.toString());
    }
    public void oppdater(View V){
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navninn.getText().toString());
        kontakt.setTelefon(telefoninn.getText().toString());
        //kontakt.setId(Long.parseLong(idinn.getText().toString()));
        db.oppdaterKontakt(kontakt);
    }
}