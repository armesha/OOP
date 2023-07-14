package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.kolekce.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextovyNacitac {

    public static Seznam<Nastroj> nactiData(String soubor) {
        Seznam<Nastroj> seznamNastroju = new SpojovySeznam<>();

        try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
            String radek;

            while ((radek = br.readLine()) != null) {
                String[] data = radek.split(";");
                if (data.length >= 4) {
                    int id = Integer.parseInt(data[0]);
                    String nazev = data[1];
                    NastrojTyp typ = NastrojTyp.valueOf(data[2]);
                    String pocetStr = data[3];
                    int pocet = 0;
                    if (!pocetStr.isEmpty()) {
                        pocet = Integer.parseInt(pocetStr);
                    }
                    String material = data.length == 5 ? data[4] : "";

                    Nastroj nastroj = switch (typ) {
                        case KLAVESOVA -> new KlavesovyNastroj(nazev, typ, pocet);
                        case SMYCCOVA -> new SmyccovyNastroj(nazev, typ, pocet);
                        case BICI -> new BiciNastroj(nazev, typ, material);
                        default -> throw new IllegalArgumentException("Neznámý typ nástroje");
                    };
                    nastroj.setId(id);
                    seznamNastroju.vlozPosledni(nastroj);
                }
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání dat z textového souboru: " + e.getMessage());
        }

        return seznamNastroju;
    }
}
