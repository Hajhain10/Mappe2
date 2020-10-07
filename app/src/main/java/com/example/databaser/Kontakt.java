package com.example.databaser;

public class Kontakt {
    //long id;
    String navn;
    String telefon;

    public Kontakt(long id, String navn, String telefon) {
       // this.id = id;
        this.navn = navn;
        this.telefon = telefon;
    }
    public Kontakt(){}

    public Kontakt(String navn,String telefon){
        this.navn = navn;
        this.telefon = telefon;
    }

   // public long getId() {return id;}

    public String getNavn() {
        return navn;
    }

    public String getTelefon() {
        return telefon;
    }

   // public void setId(long id) {this.id = id;}

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
