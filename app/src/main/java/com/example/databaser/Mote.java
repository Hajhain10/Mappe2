package com.example.databaser;

import java.util.ArrayList;

public class Mote {
    String tid;
    ArrayList<Kontakt>deltagelse;
    String sted;

    public Mote(){}

    public Mote(String tid, ArrayList<Kontakt> deltagelse, String sted) {
        this.tid = tid;
        this.deltagelse = deltagelse;
        this.sted = sted;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public ArrayList<Kontakt> getDeltagelse() {
        return deltagelse;
    }

    public void setDeltagelse(ArrayList<Kontakt> deltagelse) {
        this.deltagelse = deltagelse;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }
}
