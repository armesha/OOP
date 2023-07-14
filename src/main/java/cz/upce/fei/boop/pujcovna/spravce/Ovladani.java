package cz.upce.fei.boop.pujcovna.spravce;

import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;
import cz.upce.fei.boop.pujcovna.kolekce.Seznam;

public interface Ovladani {
    /**
     * Vloží nástroj na konec seznamu.
     *
     * @param nastroj nástroj, který chcete vložit do seznamu
     */
    void vlozNastroj(Nastroj nastroj);

    /**
     * Nastaví aktuální nástroj na první prvek seznamu.
     */
    void nastavPrvni() throws OvladaniException;

    /**
     * Nastaví aktuální nástroj na poslední prvek seznamu.
     */
    void nastavPosledni() throws OvladaniException;

    /**
     * Najde nástroj v seznamu podle zadaného ID.
     *
     * @param id ID nástroje, který hledáte
     * @return nástroj s daným ID nebo null, pokud nástroj nebyl nalezen
     */
    Nastroj najdiNastroj(int id);

    /**
     * Zjistí, zda je seznam prázdný.
     *
     * @return true, pokud je seznam prázdný, jinak false
     */
    boolean jePrazdny();

    /**
     * Zjistí, zda existuje další nástroj v seznamu.
     *
     * @return true, pokud existuje další nástroj v seznamu, jinak false
     */
    boolean jeDalsi();

    /**
     * Nastaví aktuální nástroj na další prvek seznamu.
     */
    void dalsi() throws OvladaniException;

    /**
     * Odebere aktuální nástroj ze seznamu.
     *
     * @param id ID nástroje, který chcete odebrat
     */
    void odeberNastroj(int id);

    /**
     * Vrátí první nástroj ze seznamu.
     * @return první nástroj v seznamu
     * @throws OvladaniException pokud nastane chyba při získávání prvního nástroje
     */
    Nastroj dejPrvni() throws OvladaniException;
    /**
     * Vrátí aktuální nástroj ze seznamu.
     *
     * @return aktuální nástroj v seznamu
     * @throws OvladaniException pokud nastane chyba při získávání aktuálního nástroje
     */
    Nastroj dejAktualniNastroj() throws OvladaniException;

    /**
     * Nastaví aktuální nástroj na nástroj, který je předaný v parametru.
     *
     * @param index index nástroje, který chcete nastavit jako aktuální
     * @throws OvladaniException pokud nastane chyba při nastavování aktuálního nástroje
     */
    void nastavAktualni(int index) throws OvladaniException;

    /**
     * Vygeneruje určitý počet náhodných nástrojů a vloží je do seznamu.
     *
     * @param pocet počet nástrojů, které chcete vygenerovat
     */
    void generateInstruments(int pocet);

    /**
     * Vrátí počet nalezených nástrojů v seznamu.
     *
     * @return počet nástrojů v seznamu
     */
    int pocetNalezenych();

    /**
     * Zálohuje seznam nástrojů do binárního souboru.
     *
     * @param soubor název souboru pro zálohu
     */
    void zalohuj(String soubor);

    /**
     * Obnoví seznam nástrojů z binárního souboru.
     *
     * @param soubor název souboru pro obnovu
     */
    void obnov(String soubor);

    /**
     * Uloží seznam nástrojů do textového souboru.
     *
     * @param soubor název souboru pro uložení seznamu
     */
    void ulozSeznamDoTextu(String soubor);

    /**
     * Načte seznam nástrojů z textového souboru.
     *
     * @param soubor název souboru pro načtení seznamu
     */
    void nactiSeznamZTextu(String soubor);

    /**
     * Vypíše seznam nástrojů jako pole.
     *
     * @return pole nástrojů v seznamu
     */
    Nastroj[] vypisSeznam();

    /**
     * Filtruje seznam nástrojů podle typu.
     *
     * @param typ typ nástroje, který chcete vyfiltrovat
     */
    void filtruj(NastrojTyp typ);

    /**
     * Zruší filtr.
     */
    void zrusFiltr();

    /**
     * Zruší seznam nástrojů.
     */
    void zrusSeznam();

    /**
     * Vrátí seznam nástrojů.
     *
     * @return seznam nástrojů
     */

    void predchozi();
    Seznam<Nastroj> getSeznam();
}