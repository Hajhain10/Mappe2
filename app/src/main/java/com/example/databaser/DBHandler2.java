package com.example.databaser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHandler2 extends SQLiteOpenHelper {
    static String TABLE_MOTE = "Moter";
    static String KEY_ID = "ID";
    static String KEY_TID = "Tid";
    static String KEY_STED = "Sted";
    static String KEY_DATO = "Dato";
    static int DATABASE_VERSION = 16;
    static String DATABASE_NAME = "Mote";

    public DBHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public DBHandler2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LAG_TABELL = "CREATE TABLE " + TABLE_MOTE + "("+ KEY_ID+" INTEGER PRIMARY KEY,"+KEY_DATO+ " TEXT,"+
                KEY_TID+" TEXT,"+KEY_STED+" TEXT"+")";
        Log.d("SQL", LAG_TABELL);
        sqLiteDatabase.execSQL(LAG_TABELL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MOTE);
        onCreate(sqLiteDatabase);
    }
    public void leggTilKontakter(Mote mote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TID, mote.getTid());
        values.put(KEY_STED, mote.getSted());
        values.put(KEY_DATO,mote.getDato());
        db.insert(TABLE_MOTE, null, values);
        db.close();
    }
    public long finnId(Mote m){
        long id = -1;
        String selectQuery = "SELECT * FROM "+TABLE_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                Mote mote = new Mote();
                mote.setId(cursor.getLong(0));
                mote.setDato(cursor.getString(1));
                mote.setSted(cursor.getString(3));
                mote.setTid(cursor.getString(2));
                if(m.getDato().equals(cursor.getString(1)) && m.getSted().equals(cursor.getString(3)) &&
                m.getTid().equals(cursor.getString(2))){
                    id = cursor.getLong(0);
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return id;
    }
    public Mote finnMote(Long nummer) {
        String selectQuery = "SELECT * FROM " + TABLE_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mote kontakt = new Mote();
                kontakt.setId(cursor.getLong(0));
                kontakt.setDato(cursor.getString(1));
                kontakt.setTid(cursor.getString(2));
                kontakt.setSted(cursor.getString(3));
                if (kontakt.getId() == (nummer)) {
                    return kontakt;
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return null;
    }
    public List<Mote> finnAlleKontakter(){
        List<Mote> kontaktListe = new ArrayList<Mote>();
        String selectQuery = "SELECT * FROM "+TABLE_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                Mote mote = new Mote();
                mote.setId(cursor.getLong(0));
                mote.setDato(cursor.getString(1));
                mote.setSted(cursor.getString(2));
                mote.setTid(cursor.getString(3));
                kontaktListe.add(mote);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }

    public void slettKontakt(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(id+"hhhh");
        db.delete(TABLE_MOTE, KEY_ID + " =? " , new String[]{
                String.valueOf(id)});
        db.close();
    }
    public int oppdaterKontakt(Mote kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TID, kontakt.getTid());
        values.put(KEY_DATO, kontakt.getSted());
        int endret = db.update(TABLE_MOTE, values, KEY_ID + "= ?",
                new String[]{kontakt.getTid()});
        db.close();
        return endret;
    }

}