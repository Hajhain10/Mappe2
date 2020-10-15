package com.example.databaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class oppdaterMote extends AppCompatActivity {
    EditText tiden, sted, dato;
    TextView utskrift, utskriften;
    ArrayList<Long> deltakere;
    ArrayList<Kontakt> deltakereKontakt = new ArrayList<>();
    DBHandler db;
    DBHandler4 db4;
    DBHandler2 db2;
    long id;
    String personer="";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oppdater_mote);
        id = getIntent().getLongExtra("id",0);
        db2 = new DBHandler2(this);
        db4 = new DBHandler4(this);
        db = new DBHandler(this);

        tiden = (EditText) findViewById(R.id.tid);
        sted = (EditText) findViewById(R.id.sted);
        dato = (EditText) findViewById(R.id.dato);
        utskrift = (TextView) findViewById(R.id.utskrift);
        utskriften = (TextView) findViewById(R.id.utskrift2);
        deltakere = db4.finnMoteDeltakere(id);

        for (int i = 0; i< deltakere.size(); i++){
            deltakereKontakt.add(db.finnKontakt(deltakere.get(i)));
        }
        for(int i =0; i< deltakereKontakt.size();i++){
            personer += deltakereKontakt.get(i).getNavn()+"\n";
        }
        utskrift.setText(personer);
        Mote mote = db2.finnMote(id);
        tiden.setText(mote.getTid()); sted.setText(mote.getSted());
        dato.setText(mote.getDato());


    }

    public void velgPers(View view) {
        Intent i = new Intent(this,visPersoner.class);
    }
}
