package cz.upce.fei.boop.pujcovna.perzistence;

import cz.upce.fei.boop.pujcovna.data.BiciNastroj;
import cz.upce.fei.boop.pujcovna.data.KlavesovyNastroj;
import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.data.SmyccovyNastroj;
import cz.upce.fei.boop.pujcovna.kolekce.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextovyUloziste {

    public static void ulozData(Seznam<Nastroj> seznamNastroju, String soubor) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(soubor))) {
            for (Nastroj nastroj :  seznamNastroju) {
                String radek = switch (nastroj) {
                    case KlavesovyNastroj kn ->
                            kn.getId() + ";" + kn.getNazev() + ";" + kn.getTyp() + ";" + kn.getPocetKlaves() + ";;";
                    case SmyccovyNastroj sn ->
                            sn.getId() + ";" + sn.getNazev() + ";" + sn.getTyp() + ";" + sn.getPocetStrun() + ";;";
                    case BiciNastroj bn ->
                            bn.getId() + ";" + bn.getNazev() + ";" + bn.getTyp() + ";;" + bn.getMaterial();
                    case null, default -> throw new IllegalArgumentException("Neznámý typ nástroje");
                };
                bw.write(radek);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Chyba při ukládání dat do textového souboru: " + e.getMessage());
        }
    }
}