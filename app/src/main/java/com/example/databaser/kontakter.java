package com.example.databaser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.databaser.DBHandler.KEY_PH_NO;
import static com.example.databaser.DBHandler.TABLE_KONTAKTER;

public class kontakter extends AppCompatActivity {

    EditText navninn, telefoninn, idinn;
    TextView utskrift;
    DBHandler db;
    DBHandler4 db4 = new DBHandler4(this);
    ListView lv;
    Long nummerValgt;
    Kontakt data = new Kontakt();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kontakter);

        navninn = (EditText) findViewById(R.id.navn);
        telefoninn = (EditText) findViewById(R.id.nummer);
        final Button k1 = (Button) findViewById(R.id.knapp1);
        final Button k2 = (Button) findViewById(R.id.knapp2);
        final Button k4 = (Button) findViewById(R.id.knapp4);
       // k1.setVisibility(View.GONE); k2.setVisibility(View.GONE); k4.setVisibility(View.GONE);
        //idinn = (EditText) findViewById(R.id.min_id);
        // utskrift = (TextView) findViewById(R.id.utskrift);
        db = new DBHandler(this);
        lv = (ListView)findViewById(R.id.liste);
        List<String> liste = db.visAlle();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liste);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Kontakt data = adapter.getItem(i);
                String trakk = adapter.getItem(i);
                String[]lb=trakk.split(" ");
                nummerValgt = Long.parseLong(lb[0]);
                data = db.finnKontakt(nummerValgt);
                k1.setVisibility(View.VISIBLE); k2.setVisibility(View.VISIBLE); k4.setVisibility(View.VISIBLE);
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
    public void slett(View V){
        //Long kontaktid = (Long.parseLong(telefoninn.getText().toString()));
        db.slettKontakt(nummerValgt);
        db4.slettKontakt(nummerValgt);
        recreate();
    }
    public void oppdater(View V){
        Kontakt kontakt = new Kontakt();
        kontakt.setNavn(navninn.getText().toString());
        kontakt.setTelefon(telefoninn.getText().toString());
        //kontakt.setId(Long.parseLong(idinn.getText().toString()));
        db.oppdaterKontakt(kontakt);
    }

    public void Oppdater(View view) {
        Intent i = new Intent(this,oppdaterKontakt.class);
        i.putExtra("idnummer",nummerValgt);
        startActivity(i);

    }
}