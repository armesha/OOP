/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.upce.fei.boop.pujcovna.data;

public class SmyccovyNastroj extends Nastroj {
    private int pocetStrun;

    public SmyccovyNastroj(String nazev, NastrojTyp typ, int pocetStrun) {
        super(nazev, typ);
        this.pocetStrun = pocetStrun;
    }

    public int getPocetStrun() {
        return pocetStrun;
    }

    public void setPocetStrun(int pocetStrun) {
        this.pocetStrun = pocetStrun;
    }

    @Override
    public String toString() {
        return  "{" +
                "id=" + getId() +
                ", nazev='" + getNazev() + '\'' +
                ", typ=" + getTyp() +
                ", pocetStrun=" + pocetStrun +
                '}';
    }
}

