package com.example.databaser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler4 extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER_MOTE = "MoteForKontakter";
    // static String KEY_ID = "ID";
    static String KEY_IDPERSON = "Telefon";
    static String KEY_IDFORMOTE = "IdforMote";
    static int DATABASE_VERSION = 4;
    static String DATABASE_NAME = "Mote for kontakter";

    public DBHandler4(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DBHandler4(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LAG_TABELL = "CREATE TABLE "+TABLE_KONTAKTER_MOTE+"("+ KEY_IDPERSON +
                " LONG,"+KEY_IDFORMOTE+" LONG,"+"PRIMARY KEY ("+ KEY_IDPERSON +","+KEY_IDFORMOTE+"))";
        Log.d("SQL",LAG_TABELL);
        sqLiteDatabase.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_KONTAKTER_MOTE);
        onCreate(sqLiteDatabase);
    }
    public void leggTilMoteKontakt(Mote_Kontakt kontakt){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_IDPERSON, kontakt.getNummer());
            values.put(KEY_IDFORMOTE, kontakt.getId());
            db.insert(TABLE_KONTAKTER_MOTE, null, values);
            db.close();
    }

    public boolean sjekkOmFinnes(String nummer){
        boolean unik = true;
        String selectQuery = "SELECT * FROM "+TABLE_KONTAKTER_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Kontakt kontakt = new Kontakt();
                //kontakt.setId(cursor.getLong(0));
                kontakt.setTelefon(cursor.getString(0));
                kontakt.setNavn(cursor.getString(1));
                if(kontakt.getTelefon().equals(nummer)){
                    unik = false;
                    System.out.println("Denne finnes fra før av"+ "navn : "+kontakt.getNavn()+"telefon :"+kontakt.getTelefon());
                    break;
                }
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return unik;
    }
    public List<Mote_Kontakt> finnAlleKontakter(){
        List<Mote_Kontakt> kontaktListe = new ArrayList<Mote_Kontakt>();
        String selectQuery = "SELECT * FROM "+TABLE_KONTAKTER_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Mote_Kontakt kontakt = new Mote_Kontakt();
                //kontakt.setId(cursor.getLong(0));
                kontakt.setNummer(cursor.getLong(0));
                kontakt.setId(cursor.getLong(1));
                kontaktListe.add(kontakt);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }
    public ArrayList<String> visAlle(){
        String tekst = "";
        List<Mote_Kontakt> kontakter = finnAlleKontakter();
        ArrayList<String> navn_Og_tlf = new ArrayList<>();
        for (Mote_Kontakt kontakt: kontakter) {
            navn_Og_tlf.add(kontakt.getNummer()+"\n "+kontakt.getId());
        }
        return navn_Og_tlf;
    }
    public void slettKontakt(long nummer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER_MOTE, KEY_IDPERSON + " =?", new String[]{
                String.valueOf(nummer)});
        db.close();
    }
    public void slettMote(long nummer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER_MOTE, KEY_IDFORMOTE + " =?", new String[]{
                String.valueOf(nummer)});
        db.close();
    }
    public void slettKontakt(long id, String nummer){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER_MOTE, KEY_IDPERSON + " =?" +" AND "+ KEY_IDFORMOTE + " =? ", new String[]{
                nummer,String.valueOf(id)});
        db.close();
    }
    public ArrayList<Long> finnMoteDeltakere(long nummer){
        ArrayList<Long> deltakere = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_KONTAKTER_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Mote_Kontakt kontakt = new Mote_Kontakt();
                //kontakt.setId(cursor.getLong(0));
                kontakt.setNummer(cursor.getLong(0));
                kontakt.setId(cursor.getLong(1));
               if(kontakt.getId()==nummer){
                   deltakere.add(kontakt.getNummer());
               }
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return deltakere;
    }
}
