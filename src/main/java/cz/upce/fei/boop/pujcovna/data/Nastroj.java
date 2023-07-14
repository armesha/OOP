/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.upce.fei.boop.pujcovna.data;

import java.io.Serializable;

public abstract class Nastroj implements Serializable {
    private static int citac = 0;
    private int id;
    private String nazev;
    private final NastrojTyp typ;

    public Nastroj(String nazev, NastrojTyp typ) {
        id = citac++;
        this.nazev = nazev;
        this.typ = typ;
    }

    public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public NastrojTyp getTyp() {
        return typ;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nazev='" + nazev + '\'' +
                ", typ=" + typ +
                '}';
    }
}

