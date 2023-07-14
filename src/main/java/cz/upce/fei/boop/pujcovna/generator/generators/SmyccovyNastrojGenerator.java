package cz.upce.fei.boop.pujcovna.generator.generators;

import cz.upce.fei.boop.pujcovna.data.NastrojTyp;
import cz.upce.fei.boop.pujcovna.data.SmyccovyNastroj;

import java.util.Random;

public class SmyccovyNastrojGenerator implements NastrojGenerator {
    private static final String[] NAZVY = {"Housle", "Viola", "Violoncello", "Kontrabas"};
    private static final NastrojTyp TYP = NastrojTyp.SMYCCOVA;
    private static final int MIN_POCET_STRUN = 4;
    private static final int MAX_POCET_STRUN = 6;

    @Override
    public SmyccovyNastroj generujNastroj() {
        Random nahodneCislo = new Random();

        String nazev = NAZVY[nahodneCislo.nextInt(NAZVY.length)];
        int pocetStrun = nahodneCislo.nextInt(MAX_POCET_STRUN - MIN_POCET_STRUN + 1) + MIN_POCET_STRUN;

        return new SmyccovyNastroj(nazev, TYP, pocetStrun);
    }
}

