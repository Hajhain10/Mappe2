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

public class DBHandler2 extends SQLiteOpenHelper {

    static String TABLE_MOTE = "Kontakter";
    // static String KEY_ID = "ID";
    static String KEY_TID = "Tid";
    static String KEY_STED = "Sted";
    //static String KEY_DATO = "Dato";
    static int DATABASE_VERSION = 2;
    static String DATABASE_NAME = "Mote";

    public DBHandler2(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    public DBHandler2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String LAG_TABELL = "CREATE TABLE "+TABLE_MOTE+"("+KEY_STED+" String PRIMARY KEY,"+KEY_TID+"String PRIMARY KEY"+")";
        Log.d("SQL",LAG_TABELL);
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
            values.put(KEY_STED, mote.getSted());
            values.put(KEY_TID, mote.getTid());
            db.insert(TABLE_MOTE, null, values);
            db.close();
    }
    public boolean sjekkOmFinnes(String nummer){
        boolean unik = true;
        String selectQuery = "SELECT * FROM "+TABLE_MOTE;
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
    public List<Mote> finnAlleKontakter(){
        List<Mote> kontaktListe = new ArrayList<Mote>();
        String selectQuery = "SELECT * FROM "+TABLE_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do{
                Mote mote = new Mote();
                //kontakt.setId(cursor.getLong(0));
                mote.setTid(cursor.getString(0));
                mote.setSted(cursor.getString(1));
                kontaktListe.add(mote);
            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }
    public void slettKontakt(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOTE, KEY_TID + " =? ", new String[]{
                id});
        db.close();
    }
    public int oppdaterKontakt(Mote kontakt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TID,kontakt.getTid());
        values.put(KEY_STED,kontakt.getSted());
        int endret = db.update(TABLE_MOTE,values,KEY_TID+"= ?",
                new String[]{kontakt.getSted()});
        db.close();
        return endret;
    }
    public int finnAnntallKontakter(){
        String sql = "SELECT * FROM "+TABLE_MOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }
}

