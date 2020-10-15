package com.example.databaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class visPersoner extends AppCompatActivity {
    DBHandler db;
    ListView lv;
    ArrayList<String>personListe=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listpersoner);



        db = new DBHandler(this);
        lv = (ListView)findViewById(R.id.personer);
        final List<String> liste = db.visAlle();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,liste);
        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String trakk = adapter.getItem(i);
                String[]lb=trakk.split(" ");
                //Kontakt data = db.finnKontakt(lb[lb.length-1]);
                if(lv.isItemChecked(i)){
                    lv.setItemChecked(i,true);
                    personListe.add(lb[0]);
                }else {
                    lv.setItemChecked(i,false);
                    personListe.remove(lb[0]);
                }
                //System.out.println("du trakk på "+data.getNavn() + " tl: " + data.getTelefon());
            }
        });
    }

    public void leggInnPersoner(View view) {
        Intent resultat = new Intent();
        resultat.putExtra("personer",personListe);
        setResult(RESULT_OK,resultat);
        finish();
    }
}

