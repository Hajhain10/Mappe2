package com.example.databaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class oppdaterKontakt extends AppCompatActivity {
    DBHandler db = new DBHandler(this);
    DBHandler4 db4 = new DBHandler4(this);
    EditText navninn, telefoninn;
    Kontakt k = new Kontakt();
    long nummer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oppdater_kontakt);
        navninn = (EditText) findViewById(R.id.navn);
        telefoninn = (EditText) findViewById(R.id.nummer);
        nummer = getIntent().getLongExtra("idnummer",0);
        k = db.finnKontakt(nummer);
        navninn.setText(k.getNavn()); telefoninn.setText(k.getTelefon());
    }

    public void Oppdater(View view) {
        Kontakt k = new Kontakt(navninn.getText().toString(),telefoninn.getText().toString());
        k.setId(nummer);
        db.oppdaterKontakt(k);
    }
}
