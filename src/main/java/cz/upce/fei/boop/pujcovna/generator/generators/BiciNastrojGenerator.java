package cz.upce.fei.boop.pujcovna.generator.generators;

import cz.upce.fei.boop.pujcovna.data.BiciNastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;

import java.util.Random;

public class BiciNastrojGenerator implements NastrojGenerator {
    private static final String[] MATERIALY = {"Dřevo", "Kov", "Plast", "Kůže"};
    private static final String[] NAZVY = {"Bicí souprava", "Konga", "Marimba", "Djembe"};
    private static final NastrojTyp TYP = NastrojTyp.BICI;

    @Override
    public BiciNastroj generujNastroj() {
        Random nahodneCislo = new Random();

        String nazev = NAZVY[nahodneCislo.nextInt(NAZVY.length)];
        String material = MATERIALY[nahodneCislo.nextInt(MATERIALY.length)];

        return new BiciNastroj(nazev, TYP, material);
    }
}

