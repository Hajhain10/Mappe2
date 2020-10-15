package com.example.databaser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import android.app.Service;
import android.content.Intent;
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

        Toolbar myToolbar= (Toolbar)findViewById(R.id.mintoolbar);
        myToolbar.inflateMenu(R.menu.minmeny);
        setActionBar(myToolbar);

        getSharedPreferences("PREFERENCE",MODE_PRIVATE)
                .edit()
                .putString("Melding","Du har møte på dette tidspunktet")
                .apply();

        ListView lv = (ListView) findViewById(R.id.liste);
        final ArrayList<String> al = new ArrayList<>();
        al.add("Kontakter");
        al.add("Mote");
        al.add("Knapp1");
        al.add("Knapp2");

        ArrayAdapter aA = new ArrayAdapter(this, android.R.layout.simple_list_item_1, al);

        lv.setAdapter(aA);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "clicked item"+i+" "+al.get(i).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void tilkontakter(View view) {
        Intent i = new Intent(this,Kontakt.class);
        startActivity(i);

    }

    public void tilmote(View view) {
        Intent i = new Intent(this,Mote.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.minmeny, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.kontakter:
                Toast.makeText(this, "Kontakter!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mote:
                Toast.makeText(this, "Mote", Toast.LENGTH_SHORT).show();
                break;
            case R.id.etellerannet:
                Toast.makeText(this, "etellerannet", Toast.LENGTH_SHORT).show();
                break;
                default:
                    // If wegothere, theuser'saction wasnot recognized.
                    // Invokethesuperclassto handle it.
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
