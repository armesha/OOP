package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.generator.Generator;
import cz.upce.fei.boop.pujcovna.kolekce.KolekceException;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;
import cz.upce.fei.boop.pujcovna.kolekce.SpojovySeznam;
import cz.upce.fei.boop.pujcovna.perzistence.BinarniNacitac;
import cz.upce.fei.boop.pujcovna.perzistence.BinarniUloziste;
import cz.upce.fei.boop.pujcovna.perzistence.TextovyNacitac;
import cz.upce.fei.boop.pujcovna.perzistence.TextovyUloziste;

import java.util.Random;

public class SpravaProstredku implements Ovladani {
    private Seznam<Nastroj> seznam;
    private Seznam<Nastroj> originalSeznam; //seznam pro zrušení změn
    private final Generator generator = new Generator();


    public SpravaProstredku() {
        this.seznam = new SpojovySeznam<>();
        this.originalSeznam = seznam;
    }

    @Override
    public void vlozNastroj(Nastroj nastroj) {
        seznam.vlozPosledni(nastroj);
    }

    @Override
    public void nastavPrvni() throws OvladaniException {
        try {
            seznam.nastavPrvni();
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    @Override
    public void nastavPosledni() throws OvladaniException {
        try {
            seznam.nastavPosledni();
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    @Override
    public Nastroj najdiNastroj(int id) {
        for (Nastroj n : seznam) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    @Override
    public void odeberNastroj(int id) {
        try {
            seznam.nastavPrvni();
            Nastroj predchozi = null;
            while (seznam.jeDalsi()) {
                Nastroj aktualni = seznam.dejAktualni();
                if (aktualni.getId() == id) {
                    if (predchozi == null) {
                        seznam.odeberPrvni();
                    } else {
                        seznam.odeberAktualni();
                    }
                    return;
                }
                predchozi = aktualni;
                seznam.dalsi();
            }
            // Přidáno pro zacházení s posledním prvkem
            if (seznam.dejAktualni().getId() == id) {
                seznam.odeberAktualni();
            }
        } catch (Exception e) {
            System.err.println("Chyba při odebírání nástroje s id " + id + ": " + e.getMessage());
        }
    }

    @Override
    public Nastroj dejPrvni() throws OvladaniException {
        try {
            return seznam.dejPrvni();
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    @Override
    public Nastroj dejAktualniNastroj() throws OvladaniException {
        try {
            return seznam.dejAktualni();
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    @Override
    public void nastavAktualni(int index) throws OvladaniException {
        try {
            seznam.nastavAktualni(index);
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    @Override
    public boolean jePrazdny() {
        return seznam.jePrazdny();
    }

    @Override
    public boolean jeDalsi() {
        return seznam.jeDalsi();
    }

    @Override
    public void dalsi() throws OvladaniException {
        try {
            seznam.dalsi();
        } catch (Exception e) {
            throw new OvladaniException(e);
        }
    }

    public void generateInstruments(int pocet) {
        Random rand = new Random();
        for (int i = 0; i < pocet; i++) {
            int instrumentType = rand.nextInt(3);
            switch (instrumentType) {
                case 0 -> seznam.vlozPosledni(generator.generujSmyccovyNastroj());
                case 1 -> seznam.vlozPosledni(generator.generujBiciNastroj());
                case 2 -> seznam.vlozPosledni(generator.generujKlavesovyNastroj());
            }
        }
    }

    @Override
    public int pocetNalezenych() {
        return seznam.size();
    }

    @Override
    public void zalohuj(String soubor) {
        BinarniUloziste.ulozData(soubor, seznam);
    }

    @Override
    public void obnov(String soubor) {
        seznam = BinarniNacitac.nactiData(soubor);
        originalSeznam = seznam;
    }

    @Override
    public void ulozSeznamDoTextu(String soubor) {
        TextovyUloziste.ulozData(seznam, soubor);
    }

    @Override
    public void nactiSeznamZTextu(String soubor) {
        seznam = TextovyNacitac.nactiData(soubor);
        originalSeznam = seznam;
    }

    @Override
    public Seznam<Nastroj> getSeznam() {
        return seznam;
    }

    @Override
    public void filtruj(NastrojTyp typ) {
        zrusFiltr();
        SpojovySeznam<Nastroj> vysledek = new SpojovySeznam<>();
        for (Nastroj nastroj : seznam) {
            if (typ == nastroj.getTyp()) {
                vysledek.vlozPosledni(nastroj);
            }
        }
        seznam = vysledek;
    }

    @Override
    public void zrusFiltr() {
        seznam = originalSeznam;
    }

    @Override
    public void zrusSeznam() {
        seznam.zrus();
    }

    @Override
    public void predchozi() {
        try {
            seznam.predchozi();
        } catch (KolekceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Nastroj[] vypisSeznam() {
        return seznam.stream()
                .filter(nastroj -> nastroj instanceof BiciNastroj
                        || nastroj instanceof KlavesovyNastroj
                        || nastroj instanceof SmyccovyNastroj)
                .toArray(Nastroj[]::new);
    }
}
