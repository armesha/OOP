package cz.upce.fei.boop.pujcovna.data;

public class KlavesovyNastroj extends Nastroj {
    private int pocetKlaves;

    public KlavesovyNastroj(String nazev, NastrojTyp typ, int pocetKlaves) {
        super(nazev, typ);
        this.pocetKlaves = pocetKlaves;
    }

    public int getPocetKlaves() {
        return pocetKlaves;
    }

    public void setPocetKlaves(int pocetKlaves) {
        this.pocetKlaves = pocetKlaves;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", nazev='" + getNazev() + '\'' +
                ", typ=" + getTyp() +
                ", pocetKlaves=" + pocetKlaves +
                '}';
    }
}
