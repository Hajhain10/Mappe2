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

public class DBHandler extends SQLiteOpenHelper {

    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_ID = "ID";
    static String KEY_NAME = "Navn";
    static String KEY_PH_NO = "Telefon";
    static int DATABASE_VERSION = 9;
    static String DATABASE_NAME = "Telefon Kontakter";

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LAG_TABELL = "CREATE TABLE "+TABLE_KONTAKTER+"("+KEY_ID+" INTEGER PRIMARY KEY, "+
                KEY_PH_NO+" TEXT,"+KEY_NAME+" TEXT"+")";
        Log.d("SQL",LAG_TABELL);
        sqLiteDatabase.execSQL(LAG_TABELL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_KONTAKTER);
        onCreate(sqLiteDatabase);
    }
    public void leggTilKontakter(Kontakt kontakt){
        if (sjekkOmFinnes(kontakt.telefon)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_PH_NO, kontakt.getTelefon());
            values.put(KEY_NAME, kontakt.getNavn());
            db.insert(TABLE_KONTAKTER, null, values);
            db.close();
        }
    }
    public Kontakt finnKontakt(Long nummer) {
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.setId(cursor.getLong(0));
                kontakt.setTelefon(cursor.getString(1));
                kontakt.setNavn(cursor.getString(2));
                if (kontakt.getId()==(nummer)) {
                    System.out.println("Denne finnes fra før av" + "navn : " + kontakt.getNavn() + "telefon :" + kontakt.getTelefon());
                    return kontakt;
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return null;
    }
    public boolean sjekkOmFinnes(String nummer){
        boolean unik = true;
        String selectQuery = "SELECT * FROM "+TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Kontakt kontakt = new Kontakt();
                kontakt.setId(cursor.getLong(0));
                kontakt.setTelefon(cursor.getString(1));
                kontakt.setNavn(cursor.getString(2));
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
    public List<Kontakt> finnAlleKontakter(){
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
        String selectQuery = "SELECT * FROM "+TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Kontakt kontakt = new Kontakt();
                kontakt.setId(cursor.getLong(0));
                kontakt.setTelefon(cursor.getString(1));
                kontakt.setNavn(cursor.getString(2));
                kontaktListe.add(kontakt);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }
    public ArrayList<String> visAlle(){
        String tekst = "";
        List<Kontakt> kontakter = finnAlleKontakter();
        ArrayList<String> navn_Og_tlf = new ArrayList<>();
        for (Kontakt kontakt: kontakter) {
            navn_Og_tlf.add(kontakt.getId()+" )"+kontakt.navn+"\n "+kontakt.getTelefon());
        }
        return navn_Og_tlf;
    }
    public void slettKontakt(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_ID + " =? ", new String[]{
                String.valueOf(id)});
        db.close();
    }
    public int oppdaterKontakt(Kontakt kontakt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,kontakt.getNavn());
        values.put(KEY_PH_NO,kontakt.getTelefon());
        int endret = db.update(TABLE_KONTAKTER,values,KEY_ID+"= ?",
                new String[]{String.valueOf(kontakt.getId())});
        db.close();
        return endret;
    }
    public int finnAnntallKontakter(){
        String sql = "SELECT * FROM "+TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }
}
