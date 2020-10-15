package com.example.databaser;

public class Mote_Kontakt {
    long id, nummer;

    public Mote_Kontakt(){}
    public Mote_Kontakt(long nummer, long id) {
        this.id = id;
        this.nummer = nummer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNummer() {
        return nummer;
    }

    public void setNummer(long nummer) {
        this.nummer = nummer;
    }
}
