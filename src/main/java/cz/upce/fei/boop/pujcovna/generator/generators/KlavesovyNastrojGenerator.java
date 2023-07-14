package cz.upce.fei.boop.pujcovna.generator.generators;

import cz.upce.fei.boop.pujcovna.data.KlavesovyNastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;

import java.util.Random;

public class KlavesovyNastrojGenerator implements NastrojGenerator {
    private static final String[] NAZVY = {"Piano", "Varhany", "Syntezátor", "Cembalo"};
    private static final NastrojTyp TYP = NastrojTyp.KLAVESOVA;
    private static final int MIN_POCET_KLAVES = 25;
    private static final int MAX_POCET_KLAVES = 88;

    @Override
    public KlavesovyNastroj generujNastroj() {
        Random nahodneCislo = new Random();

        String nazev = NAZVY[nahodneCislo.nextInt(NAZVY.length)];
        int pocetKlaves = nahodneCislo.nextInt(MAX_POCET_KLAVES - MIN_POCET_KLAVES + 1) + MIN_POCET_KLAVES;

        return new KlavesovyNastroj(nazev, TYP, pocetKlaves);
    }
}

