package cz.upce.fei.boop.pujcovna.command;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.generator.*;
import cz.upce.fei.boop.pujcovna.kolekce.*;
import cz.upce.fei.boop.pujcovna.perzistence.*;

import java.util.Random;
import java.util.Scanner;

public class Commands implements Command {

    private boolean isRunning = true;
    private Seznam<Nastroj> seznam;
    final Generator generator = new Generator();

    public boolean isRunning() {
        return isRunning;
    }

    public Commands() {
        seznam = new SpojovySeznam<>();
    }

    @Override
    public void execute() throws KolekceException {
        Scanner scanner = new Scanner(System.in);
        String prikaz = scanner.nextLine();

        switch (prikaz) {
            case "help", "h" -> vypisHelp();
            case "novy", "no" -> {
                try {
                    vytvorNovy();
                } catch (KolekceException e) {
                    throw new RuntimeException(e);
                }
            }
            case "najdi", "na", "n" -> najdiPodleId();
            case "odeber", "od" -> odeberPodleId();
            case "dej" -> vypisAktualni();
            case "edituj", "edit" -> upravAktualni();
            case "vyjmi" -> vyjmiAktualni();
            case "prvni", "pr" -> nastavPrvni();
            case "dalsi", "da" -> prejdiNaDalsi();
            case "posledni", "po" -> nastavPosledni();
            case "pocet" -> zobrazPocet();
            case "obnov" -> obnovSeznam();
            case "zalohuj" -> zalohujSeznam();
            case "vypis" -> vypisSeznam();
            case "nactitext", "nt" -> nactiSeznamZTextu();
            case "uloztext", "ut" -> ulozSeznamDoTextu();
            case "generuj", "g" -> generujData();
            case "zrus" -> zrusSeznam();
            case "exit" -> ukonciProgram();
            default -> System.out.println("Neplatný příkaz.");
        }
    }

    public void vypisHelp() {
        String napoveda = """
                 help, h     - výpis příkazů
                 novy,no     - vytvoř novou instanci a vlož data za aktuální prvek
                 najdi,na,n  - najdi v seznamu data podle hodnoty nějakém atributu
                 odeber,od   - odeber data ze seznamu podle nějaké hodnoty atributu\s
                 dej         - zobraz aktuální data v seznamu
                 edituj,edit - edituj aktuální data v seznamu
                 vyjmi       - vyjmi aktuální data ze seznamu
                 prvni,pr    - nastav jako aktuální první data v seznamu
                 dalsi,da    - přejdi na další data
                 posledni,po - přejdi na poslední data
                 pocet       - zobraz počet položek v seznamu
                 obnov       - obnov seznam data z binárního souboru
                 zalohuj     - zálohuj seznam dat do binárního souboru
                 vypis       - zobraz seznam dat
                 nactitext,nt- načti seznam data z textového souboru
                 uloztext,ut - ulož seznam data do textového souboru
                 generuj,g   - generuj náhodně data pro testování
                 zrus        - zruš všechny data v seznamu
                 exit        - ukončení programu
                """;
        System.out.println(napoveda);
    }

    public void vytvorNovy() throws KolekceException {
        if (seznam.jePrazdny()) {
            System.out.println("Není žádný aktuální hudební nástroj, nelze vytvořit nový.");
            System.out.println("*novy, no - vytvoř novou instanci a vlož data za aktuální prvek");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte název nového nástroje:");
        String nazev = scanner.nextLine();

        System.out.println("Vyberte typ nástroje:");
        System.out.println("1 - Bicí nástroj");
        System.out.println("2 - Klávesový nástroj");
        System.out.println("3 - Smyčcový nástroj");
        int typ = Integer.parseInt(scanner.nextLine());

        switch (typ) {
            case 1 -> {
                System.out.println("Zadejte materiál:");
                String material = scanner.nextLine();
                BiciNastroj novyBiciNastroj = new BiciNastroj(nazev, NastrojTyp.BICI, material);
                seznam.vlozZaAktualni(novyBiciNastroj);
            }
            case 2 -> {
                System.out.println("Zadejte počet kláves:");
                int pocetKlaves = Integer.parseInt(scanner.nextLine());
                KlavesovyNastroj novyKlavesovyNastroj = new KlavesovyNastroj(nazev, NastrojTyp.KLAVESOVA, pocetKlaves);
                seznam.vlozZaAktualni(novyKlavesovyNastroj);
            }
            case 3 -> {
                System.out.println("Zadejte počet strun:");
                int pocetStrun = Integer.parseInt(scanner.nextLine());
                SmyccovyNastroj novySmyccovyNastroj = new SmyccovyNastroj(nazev, NastrojTyp.SMYCCOVA, pocetStrun);
                seznam.vlozZaAktualni(novySmyccovyNastroj);
            }
            default -> System.out.println("Neplatný výběr typu nástroje.");
        }
    }

    public void najdiPodleId() {
        Scanner scanner = new Scanner(System.in);
        Nastroj nalezeny = null;
        System.out.println("Zadejte ID hledaného nástroje:");
        int id = Integer.parseInt(scanner.nextLine());
        for (Nastroj nastroj : seznam) {
            if (nastroj.getId() == id) {
                nalezeny = nastroj;
            }
        }
        if (nalezeny != null) {
            System.out.println(nalezeny);
        } else {
            System.out.println("Nástroj s ID " + id + " nebyl nalezen.");
        }
    }

    public void odeberPodleId() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte id nástroje, který chcete odebrat:");
        int id = Integer.parseInt(scanner.nextLine());
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
                    System.out.println("Nástroj s id " + id + " byl odebrán ze seznamu.");
                    return;
                }
                predchozi = aktualni;
                seznam.dalsi();
            }
            // Přidáno pro zacházení s posledním prvkem
            if (seznam.dejAktualni().getId() == id) {
                seznam.odeberAktualni();
                System.out.println("Nástroj s id " + id + " byl odebrán ze seznamu.");
            }
        } catch (KolekceException e) {
            System.err.println("Chyba při odebírání nástroje s id " + id + ": " + e.getMessage());
        }
    }

    public void vypisAktualni() throws KolekceException {
        Nastroj aktualni = seznam.dejAktualni();
        if (aktualni == null) {
            System.out.println("Aktuální prvek neexistuje.");
        } else {
            System.out.println("Aktuální prvek: " + aktualni);
        }
    }

    public void upravAktualni() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte ID nástroje, který chcete upravit:");
        int id = Integer.parseInt(scanner.nextLine());
        Nastroj aktualni = null;
        for (Nastroj nastroj : seznam) {
            if (nastroj.getId() == id) {
                aktualni = nastroj;
                break;
            }
        }

        if (aktualni == null) {
            System.out.println("Nástroj s ID " + id + " nebyl nalezen.");
            return;
        }

        System.out.println("Zadejte nový název nástroje:");
        String novyNazev = scanner.nextLine();
        aktualni.setNazev(novyNazev);

        switch (aktualni) {
            case BiciNastroj biciNastroj -> {
                System.out.println("Zadejte nový materiál:");
                String novyMaterial = scanner.nextLine();
                biciNastroj.setMaterial(novyMaterial);
            }
            case KlavesovyNastroj klavesovyNastroj -> {
                System.out.println("Zadejte nový počet kláves:");
                int novyPocetKlaves = Integer.parseInt(scanner.nextLine());
                klavesovyNastroj.setPocetKlaves(novyPocetKlaves);
            }
            case SmyccovyNastroj smyccovyNastroj -> {
                System.out.println("Zadejte nový počet strun:");
                int novyPocetStrun = Integer.parseInt(scanner.nextLine());
                smyccovyNastroj.setPocetStrun(novyPocetStrun);
            }
            case null, default -> {
            }
        }

        System.out.println("Aktuální nástroj byl upraven.");
    }

    public void vyjmiAktualni() throws KolekceException {
        seznam.odeberAktualni();
        System.out.println("Aktuální prvek byl vyjmut ze seznamu.");
    }

    public void nastavPrvni() throws KolekceException {
        seznam.nastavPrvni();
        System.out.println("Aktuální prvek byl nastaven na první prvek seznamu.");
    }

    public void prejdiNaDalsi() throws KolekceException {
        if (!seznam.jeDalsi()) {
            System.out.println("Aktuální prvek je poslední prvek seznamu.");
            return;
        }
        seznam.dalsi();
        System.out.println("Aktuální prvek byl nastaven na další prvek seznamu.");
    }

    public void nastavPosledni() throws KolekceException {
        seznam.nastavPosledni();
        System.out.println("Aktuální prvek byl nastaven na poslední prvek seznamu.");
    }

    public void zobrazPocet() {
        System.out.println("Počet nástrojů v seznamu: " + seznam.size());
    }

    public void obnovSeznam() {
        String soubor = "defaultFile.bin";
        System.out.println("Obnovuji seznam nástrojů z binárního souboru: " + soubor);

        Seznam<Nastroj> nactenySeznam = BinarniNacitac.nactiData(soubor);

        if (nactenySeznam != null && !nactenySeznam.jePrazdny()) {
            seznam = nactenySeznam;
            System.out.println("Seznam nástrojů byl úspěšně obnoven z binárního souboru: " + soubor);
        } else {
            System.out.println("Chyba při obnově seznamu nástrojů z binárního souboru: " + soubor);
        }
    }

    public void zalohujSeznam() {
        String soubor = "defaultFile.bin";
        System.out.println("Zálohování seznamu nástrojů do binárního souboru: " + soubor);

        BinarniUloziste.ulozData(soubor, seznam);
    }

    public void vypisSeznam() {
        for (Nastroj nastroj : seznam) {
            if (nastroj instanceof BiciNastroj) {
                System.out.println(((BiciNastroj) nastroj));
            } else if (nastroj instanceof KlavesovyNastroj) {
                System.out.println(((KlavesovyNastroj) nastroj));
            } else if (nastroj instanceof SmyccovyNastroj) {
                System.out.println(((SmyccovyNastroj) nastroj));
            }
        }
    }

    public void nactiSeznamZTextu() {
        String soubor = "defaultFile.txt";
        System.out.println("Načítám seznam nástrojů ze souboru: " + soubor);

        Seznam<Nastroj> nactenySeznam = TextovyNacitac.nactiData(soubor);

        if (!nactenySeznam.jePrazdny()) {
            seznam = nactenySeznam;
            System.out.println("Seznam nástrojů byl úspěšně načten ze souboru: " + soubor);
        } else {
            System.out.println("Chyba při načítání seznamu nástrojů ze souboru: " + soubor);
        }
    }

    public void ulozSeznamDoTextu() {
        String soubor = "defaultFile.txt";
        System.out.println("Ukládám seznam nástrojů do souboru: " + soubor);

        TextovyUloziste.ulozData(seznam, soubor);
    }

    public void generujData() throws KolekceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kolik nástrojů chcete vygenerovat?");
        int pocetNastroju = scanner.nextInt();
        Random rand = new Random();
        for (int i = 0; i < pocetNastroju; i++) {
            int typNastroje = rand.nextInt(3);
            switch (typNastroje) {
                case 0 -> seznam.vlozPosledni(generator.generujSmyccovyNastroj());
                case 1 -> seznam.vlozPosledni(generator.generujBiciNastroj());
                case 2 -> seznam.vlozPosledni(generator.generujKlavesovyNastroj());
            }
        }

        seznam.nastavPosledni();
        vypisSeznam();
    }

    public void zrusSeznam() {
        seznam.zrus();
        System.out.println("Seznam nástrojů byl úspěšně zrušen.");
    }

    public void ukonciProgram() {
        isRunning = false;
        System.out.println("Ukončení programu.");
    }
}