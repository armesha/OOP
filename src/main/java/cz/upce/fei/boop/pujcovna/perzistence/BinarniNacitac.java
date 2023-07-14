package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.kolekce.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BinarniNacitac {

    public static Seznam<Nastroj> nactiData(String soubor) {
        Seznam<Nastroj> seznamNastroju = new SpojovySeznam<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(soubor))) {
            Object obj = ois.readObject();

            if (obj instanceof Seznam) {
                seznamNastroju = (Seznam<Nastroj>) obj;
            } else {
                System.err.println("Data v binárním souboru nejsou ve správném formátu.");
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání dat z binárního souboru: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Chyba při načítání dat z binárního souboru, neznámá třída: " + e.getMessage());
        }

        return seznamNastroju;
    }
}
