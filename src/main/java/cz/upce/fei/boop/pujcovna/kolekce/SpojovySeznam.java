package cz.upce.fei.boop.pujcovna.kolekce;

/**
 *
 * @author armes
 * @param <E>
 */

import cz.upce.fei.boop.pujcovna.data.Nastroj;

import java.io.Serializable;
import java.util.*;

public class SpojovySeznam<E extends Nastroj> implements Seznam<E>, Serializable {

    private Prvek<E> prvni;
    private Prvek<E> posledni;
    private Prvek<E> aktualni;
    private int velikost;

    public SpojovySeznam() {
        prvni = null;
        posledni = null;
        aktualni = null;
        velikost = 0;
    }

    private static class Prvek<E extends Nastroj> implements Serializable {

        final private E data;
        private Prvek<E> dalsi;
        private Prvek<E> predchozi;

        public Prvek(E data) {
            this.data = data;
            this.dalsi = null;
            this.predchozi = null;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (prvni == null) {
            throw new KolekceException("Seznam je prázdný.");
        }
        aktualni = prvni;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (velikost == 0) {
            throw new KolekceException("Seznam je prázdný.");
        }
        aktualni = posledni;
    }

    @Override
    public void dalsi() throws KolekceException {
        kontrolaAktualniho();
        if (aktualni == posledni) {
            throw new KolekceException("Aktuální prvek je poslední v seznamu.");
        }
        aktualni = aktualni.dalsi;
    }

    @Override
    public void vlozPrvni(E prvek) {
        if (prvek == null) {
            throw new NullPointerException("prvek == null");
        }
        Prvek<E> novyPrvek = new Prvek<>(prvek);
        if (prvni == null) {
            prvni = novyPrvek;
            posledni = novyPrvek;
        } else {
            novyPrvek.dalsi = prvni;
            prvni.predchozi = novyPrvek;
            prvni = novyPrvek;
        }
        velikost++;
    }

    @Override
    public boolean jeDalsi() {
        return aktualni != null && aktualni.dalsi != null;
    }

    @Override
    public void vlozPosledni(E data) {
        if (data == null) {
            throw new NullPointerException("Data musí být nenulová.");
        }
        Prvek<E> novyPrvek = new Prvek<>(data);
        if (prvni == null) {
            prvni = novyPrvek;
        } else {
            novyPrvek.predchozi = posledni;
            posledni.dalsi = novyPrvek;
        }
        posledni = novyPrvek;
        velikost++;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        if (data == null) {
            throw new NullPointerException("Data musí být nenulová.");
        }

        kontrolaAktualniho();

        Prvek<E> novyPrvek = new Prvek<>(data);

        if (aktualni == posledni) {
            vlozPosledni(data);
        } else {
            novyPrvek.dalsi = aktualni.dalsi;
            aktualni.dalsi.predchozi = novyPrvek;
            aktualni.dalsi = novyPrvek;
            novyPrvek.predchozi = aktualni;
            velikost++;
        }
    }

    @Override
    public boolean jePrazdny() {
        return velikost == 0;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (prvni == null) {
            throw new KolekceException("Seznam je prázdný");
        }
        return prvni.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (posledni == null) {
            throw new KolekceException("Seznam je prázdný");
        }
        return posledni.data;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        kontrolaAktualniho();
        return aktualni.data;
    }

    @Override
    public E dejZaAktualnim() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        kontrolaAktualniho();
        if (aktualni.dalsi == null) {
            throw new KolekceException("Aktuální prvek nemá následníka.");
        }
        return aktualni.dalsi.data;
    }

    @Override
    public E odeberPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        E odebirany = prvni.data;
        if (aktualni == prvni) {
            aktualni = null;
        }
        if (velikost == 1) {
            prvni = null;
            posledni = null;
        } else {
            prvni = prvni.dalsi;
            prvni.predchozi = null;
        }
        velikost--;
        return odebirany;
    }

    @Override
    public E odeberPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        E odebirany = posledni.data;

        if (aktualni == posledni) {
            aktualni = null;
        }
        if (velikost == 1) {
            prvni = null;
            posledni = null;
        } else {
            posledni = posledni.predchozi;
            posledni.dalsi = null;
        }

        velikost--;
        return odebirany;
    }

    public void kontrolaAktualniho() throws KolekceException {
        if (aktualni == null) {
            throw new KolekceException("Aktuální prvek není nastaven.");
        }
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        kontrolaAktualniho();

        if (aktualni == prvni) {
            return odeberPrvni();
        } else if (aktualni == posledni) {
            return odeberPosledni();
        }
        E odebirany = aktualni.data;
        aktualni.predchozi.dalsi = aktualni.dalsi;
        aktualni.dalsi.predchozi = aktualni.predchozi;

        aktualni = aktualni.dalsi;

        velikost--;
        return odebirany;
    }



    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException("Seznam je prázdný.");
        }
        kontrolaAktualniho();

        if (aktualni == posledni) {
            throw new KolekceException("Aktuální prvek nemá následníka.");
        }

        E odebirany = aktualni.dalsi.data;
        if (aktualni.dalsi == posledni) {
            odeberPosledni();
        } else {
            aktualni.dalsi = aktualni.dalsi.dalsi;
            aktualni.dalsi.predchozi = aktualni;
            velikost--;
        }
        return odebirany;
    }

    @Override
    public int size() {
        return velikost;
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        velikost = 0;
    }

    @Override
    public void nastavAktualni(int index) throws KolekceException {
        if (index < 0 || index >= velikost) {
            throw new KolekceException("Index je mimo rozsah.");
        }

        aktualni = prvni;
        for (int i = 0; i < index; i++) {
            aktualni = aktualni.dalsi;
        }
    }

    @Override
    public void predchozi() throws KolekceException {
        kontrolaAktualniho();
        if (aktualni == prvni) {
            throw new KolekceException("Aktuální prvek je první v seznamu.");
        }
        aktualni = aktualni.predchozi;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Prvek<E> aktualniPrvek = prvni;

            @Override
            public boolean hasNext() {
                return aktualniPrvek != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E data = aktualniPrvek.data;
                aktualniPrvek = aktualniPrvek.dalsi;
                return data;
            }
        };
    }
}

