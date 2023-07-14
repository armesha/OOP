package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class BinarniUloziste {

    public static void ulozData(String soubor, Seznam<Nastroj> seznamNastroju) {
        try (FileOutputStream fos = new FileOutputStream(soubor);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(seznamNastroju);
        } catch (IOException e) {
            System.err.println("Chyba při ukládání dat do binárního souboru: " + e.getMessage());
        }
    }
}
