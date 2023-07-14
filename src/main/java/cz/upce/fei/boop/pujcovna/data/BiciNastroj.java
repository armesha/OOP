/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.upce.fei.boop.pujcovna.data;

public class BiciNastroj extends Nastroj {
    private String material;

    public BiciNastroj(String nazev, NastrojTyp typ, String material) {
        super(nazev, typ);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", nazev='" + getNazev() + '\'' +
                ", typ=" + getTyp() +
                ", material='" + material + '\'' +
                '}';
    }
}

