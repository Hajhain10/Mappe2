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

    }

    public void tilkontakter(View view) {
        Intent i = new Intent(this,kontakter.class);
        startActivity(i);

    }

    public void tilmote(View view) {
        Intent i = new Intent(this,moter.class);
        startActivity(i);
    }
}