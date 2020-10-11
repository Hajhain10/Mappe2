package com.example.databaser;

import java.util.ArrayList;

public class Mote {
    String tid,dato="";
    String sted="";
    long id;

    public Mote(){}

    public Mote(String tid, String dato, String sted) {
        this.tid = tid;
        this.dato = dato;
        this.sted = sted;
    }

    public Mote(String tid, String dato, String sted, long id) {
        this.tid = tid;
        this.dato = dato;
        this.sted = sted;
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }
}
