package com.example.databaser;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.LoggingMXBean;

public class moter extends AppCompatActivity {

    EditText tiden, sted, dato;
    TextView utskrift, utskriften;
    ListView lv;
    String nummer="";
    long id = 0;
    ArrayList<Kontakt>deltakere;
    DBHandler ko;
    DBHandler2 db;
    DBHandler4 db4;
    DatePickerDialog velgdato;
    TimePickerDialog velgtid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mote);

        tiden = (EditText) findViewById(R.id.tid);
        sted = (EditText) findViewById(R.id.sted);
        dato = (EditText) findViewById(R.id.dato);
        lv = (ListView) findViewById(R.id.liste);
        utskrift = (TextView) findViewById(R.id.utskrift);
        utskriften = (TextView) findViewById(R.id.utskrift2);
        db = new DBHandler2(this);
        db4 = new DBHandler4(this);

        List<String> liste = visAlle();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,liste);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Kontakt data = adapter.getItem(i);
                String trakk = adapter.getItem(i);
                String[] split =trakk.split(" ");
                id = Long.parseLong(split[0]);
                System.out.println(split[0]+"mellomrom");
                //String[]lb=trakk.split(" ");
            }
        });
    }

    public void leggtil(View V){
        Mote kontakt= new Mote(tiden.getText().toString(),dato.getText().toString(), sted.getText().toString());
        db.leggTilKontakter(kontakt);
        long id = db.finnId(kontakt);
        utskrift.setText(String.valueOf(id));
       Mote_Kontakt mote_kontakt;
            for (int i = 0; i < deltakere.size(); i++){
                mote_kontakt = new Mote_Kontakt(deltakere.get(i).getId(),id);
                db4.leggTilMoteKontakt(mote_kontakt);
            }
            utskriften.setText(Arrays.toString(db4.visAlle().toArray()));
        System.out.println("jegerher "+Arrays.toString(db4.visAlle().toArray()));
         //  recreate();
        //utskrift.setText(Arrays.toString(db4.visAlle().toArray()));
        // recreate();
    }
    public List<String> visAlle(){
        String tekst = "";
        List<Mote> kontakter = db.finnAlleKontakter();
        ArrayList<String> moter = new ArrayList<>();
        for (Mote kontakt: kontakter) {
            moter.add(kontakt.getId()+" ) " + kontakt.getDato() +" "+kontakt.getTid()+"\n "+kontakt.getSted());
        }
        return moter;
    }
    public void slett(View V){
        db.slettKontakt(id);
        db4.slettMote(id);
        recreate();
        //må sletter db4 sin mote nummer også.
    }
    public void oppdater(View V){
        Mote kontakt = new Mote();
        kontakt.setTid(tiden.getText().toString());
        kontakt.setSted(sted.getText().toString());
        //kontakt.setId(Long.parseLong(idinn.getText().toString()));
        db.oppdaterKontakt(kontakt);
    }

    public void velgDato(View view) {
        Calendar kalender = Calendar.getInstance();
        int dag = kalender.get(Calendar.DAY_OF_MONTH);
        int maned = kalender.get(Calendar.MONTH);
        int aar = kalender.get(Calendar.YEAR);

        velgdato = new DatePickerDialog(moter.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int aar, int maned, int dag) {
                        dato.setText(String.format("%02d",dag)+"."+String.format("%02d",(maned))+"."+aar);
                    }
                }, aar, maned, dag);
        velgdato.show();
    }

    public void velgtid(View view) {
        Calendar tid = Calendar.getInstance();
        int time = tid.get(Calendar.HOUR);
        int minutt = tid.get(Calendar.MINUTE);

        velgtid = new TimePickerDialog(moter.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int time, int minutt) {

                tiden.setText(String.format("%02d",time)+":"+String.format("%02d",minutt));
            }
        },time,minutt,true);
        velgtid.show();
    }

    public void velgPers(View view) {
        Intent i = new Intent(this,visPersoner.class);
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        deltakere = new ArrayList<>();
        StringBuilder utskriften = new StringBuilder();
        ko = new DBHandler(this);
        Kontakt k = new Kontakt();
        if (requestCode == 1 && resultCode == RESULT_OK){
            ArrayList<String> innNummer = data.getStringArrayListExtra("personer");
           for (int i = 0; i < innNummer.size(); i++){
              // System.out.println(k.getNavn());
             // k = ko.finnKontakt(Long.parseLong(innNummer.get(i)));
               k = ko.finnKontakt(Long.parseLong(innNummer.get(i)));
               deltakere.add(k);
               utskriften.append(k.getId()+", ");
           }
           utskriften.delete((utskriften.length()-2),utskriften.length());
           utskrift.setText(utskriften.toString() + deltakere.size());
        }
    }

    public void oppdaterMote(View view) {
        Intent i =  new Intent(this,oppdaterMote.class);
        i.putExtra("id",id);
        startActivity(i);
    }
}