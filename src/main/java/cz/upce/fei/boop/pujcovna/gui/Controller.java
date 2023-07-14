package cz.upce.fei.boop.pujcovna.gui;

import cz.upce.fei.boop.pujcovna.data.*;
import cz.upce.fei.boop.pujcovna.gui.dialogy.DialogBiciNastroj;
import cz.upce.fei.boop.pujcovna.gui.dialogy.DialogKlavesovyNastroj;
import cz.upce.fei.boop.pujcovna.gui.dialogy.DialogSmyccovyNastroj;
import cz.upce.fei.boop.pujcovna.gui.dialogy.ZakladniDialog;
import cz.upce.fei.boop.pujcovna.spravce.Ovladani;
import cz.upce.fei.boop.pujcovna.spravce.OvladaniException;
import cz.upce.fei.boop.pujcovna.spravce.SpravaProstredku;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

public class Controller {
    @FXML
    private ListView<Nastroj> listView;
    @FXML
    private ChoiceBox<String> filtr;
    @FXML
    private ChoiceBox<String> novyNastroj;
    final Ovladani commands = new SpravaProstredku();

    public void initialize() {
        nastavitVyberNastroje();
        nastavitNovyNastrojVolba();
        nastavitFiltrVolba();
    }

    private void nastavitVyberNastroje() {
        listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            if (newIndex.intValue() != -1) {
                try {
                    commands.nastavAktualni(newIndex.intValue());
                } catch (OvladaniException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void nastavitNovyNastrojVolba() {
        novyNastroj.getItems().addAll("Nový nástroj", "Bicí nástroj", "Klávesový nástroj", "Smyčcový nástroj");
        novyNastroj.getSelectionModel().selectFirst();
        novyNastroj.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Nastroj nastroj = null;
            switch (newValue) {
                case "Bicí nástroj" -> {
                    DialogBiciNastroj dialog = new DialogBiciNastroj();
                    dialog.showAndWait();
                    nastroj = dialog.getNovyNastroj();
                }
                case "Klávesový nástroj" -> {
                    DialogKlavesovyNastroj dialog = new DialogKlavesovyNastroj();
                    dialog.showAndWait();
                    nastroj = dialog.getNovyNastroj();
                }
                case "Smyčcový nástroj" -> {
                    DialogSmyccovyNastroj dialog = new DialogSmyccovyNastroj();
                    dialog.showAndWait();
                    nastroj = dialog.getNovyNastroj();
                }
            }
            novyNastroj.setValue("Nový nástroj");
            if (nastroj != null) {
                commands.vlozNastroj(nastroj);
            }
            if (commands.pocetNalezenych() == 1) {
                try {
                    commands.nastavPrvni();
                } catch (OvladaniException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                updateListView();
            } catch (OvladaniException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void nastavitFiltrVolba() {
        filtr.getItems().addAll("Bez filtru", "Klávesové nástroje", "Bicí nástroje", "Smyčcové nástroje");
        filtr.setValue("Bez filtru");
        filtr.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Klávesové nástroje" -> commands.filtruj(NastrojTyp.KLAVESOVA);
                case "Bicí nástroje" -> commands.filtruj(NastrojTyp.BICI);
                case "Smyčcové nástroje" -> commands.filtruj(NastrojTyp.SMYCCOVA);
                default -> commands.zrusFiltr();
            }
            try {
                commands.nastavPrvni();
                updateListView();
            } catch (OvladaniException e) {
                alert("Filtr", "Chyba", "V seznamu nejsou žádné nástroje pro zvolený filtr");
            }
        });
    }

    @FXML
    private void handleUpravButton() throws OvladaniException {
        if (commands.dejAktualniNastroj() == null) {
            return;
        }

        ZakladniDialog dialog;
        if (commands.dejAktualniNastroj() instanceof BiciNastroj) {
            dialog = new DialogBiciNastroj(commands.dejAktualniNastroj());
        } else if (commands.dejAktualniNastroj() instanceof KlavesovyNastroj) {
            dialog = new DialogKlavesovyNastroj(commands.dejAktualniNastroj());
        } else if (commands.dejAktualniNastroj() instanceof SmyccovyNastroj) {
            dialog = new DialogSmyccovyNastroj(commands.dejAktualniNastroj());
        } else {
            throw new UnsupportedOperationException("Unsupported Nastroj type");
        }

        dialog.showAndWait();
        updateListView();
    }

    private void updateListView() throws OvladaniException {
        listView.getItems().clear();

        if (!commands.jePrazdny()) {
            Nastroj n = commands.dejAktualniNastroj();
            commands.nastavPrvni();

            while (commands.jeDalsi()) {
                listView.getItems().add(commands.dejAktualniNastroj());
                commands.dalsi();
            }
            listView.getItems().add(commands.dejAktualniNastroj());

            commands.nastavPrvni();
            while (commands.dejAktualniNastroj() != n) {
                commands.dalsi();
            }
            listView.getSelectionModel().select(commands.dejAktualniNastroj());
        }
    }

    @FXML
    private void handleGenerateData() throws OvladaniException {
        filtr.setValue("Bez filtru");
        int pocet = pozadavekNaCislo ("Generovat nástroje", "Zadejte počet nástrojů pro generování", "Počet nástrojů:");
        commands.generateInstruments(pocet);
        commands.nastavPrvni();
        updateListView();
        commands.nastavPrvni();
    }

    @FXML
    public void dalsi() throws OvladaniException {
        if (!commands.jeDalsi()) {
            commands.nastavPrvni();
        } else {
            commands.dalsi();
        }

        updateListView();
    }

    @FXML
    private void predchozi() throws OvladaniException {
        try {
            commands.predchozi();
        } catch (Exception e) {
            commands.nastavPosledni();
        }
        updateListView();
    }

    @FXML
    public void prvni() throws OvladaniException {
        commands.nastavPrvni();
        updateListView();
    }

    @FXML
    public void posledni() throws OvladaniException {
        commands.nastavPosledni();
        updateListView();
    }

    @FXML
    public void odeber() throws OvladaniException {
        if (commands.jePrazdny()) {
            alert("Chyba", "Seznam je prázdný", "Seznam je prázdný, nelze odebrat žádný nástroj");
            return;
        }

        commands.odeberNastroj(commands.dejAktualniNastroj().getId());

        if (commands.jePrazdny()) {
            listView.getItems().clear();
        } else if (!commands.jeDalsi()) {
                commands.nastavPrvni();
            }

        updateListView();
    }

    @FXML
    public void dejPocet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Počet nástrojů");
        alert.setHeaderText("Počet nástrojů v seznamu");
        alert.setContentText("Počet nástrojů v seznamu: " + commands.pocetNalezenych());
        alert.showAndWait();
    }

    @FXML
    public void vymazatSeznam() throws OvladaniException {
        filtr.setValue("Bez filtru");
        listView.getItems().clear();
        commands.zrusSeznam();
        updateListView();
    }

    @FXML
    public void zalohovatSeznam() {
        String s = "seznam.bin";
        if (commands.jePrazdny()) {
            alert("Chyba", "Seznam je prázdný", "Seznam je prázdný, nalezené nástroje nelze uložit");
            return;
        }
        String fileName = requestFileName(s);
        if (fileName != null) {
            commands.zalohuj(fileName);
        }
    }

    @FXML
    public void obnovitSeznam() throws OvladaniException {
        String s = "seznam.bin";
        String fileName = requestFileName(s);
        if (fileName != null) {
            commands.obnov(fileName);
            commands.nastavPrvni();
            updateListView();
            filtr.setValue("Bez filtru");
        }
    }

    @FXML
    public void ulozSeznam() {
        String s = "seznam.txt";
        if (commands.jePrazdny()) {
            alert("Chyba", "Seznam je prázdný", "Seznam je prázdný, nalezené nástroje nelze uložit");
            return;
        }
        String fileName = requestFileName(s);
        if (fileName != null) {
            commands.ulozSeznamDoTextu(fileName);
        }
    }

    @FXML
    public void nactiSeznam() throws OvladaniException {
        String s = "seznam.txt";
        String fileName = requestFileName(s);
        if (fileName != null) {
            commands.nactiSeznamZTextu(fileName);
            commands.nastavPrvni();
            updateListView();
            filtr.setValue("Bez filtru");
        }
    }

    private String requestFileName(String s) {
        TextInputDialog dialog = new TextInputDialog(s);
        dialog.setTitle("Zadejte název souboru");
        dialog.setHeaderText("Zadejte název souboru pro uložení nebo načtení");
        dialog.setContentText("Název souboru:");
        return dialog.showAndWait().orElse(null);
    }

    @FXML
    public void najitId() throws OvladaniException {
        int id = pozadavekNaCislo ("Hledat nástroj podle ID", "Zadejte ID nástroje, který chcete najít", "ID nástroje:");
        Nastroj n = commands.najdiNastroj(id);
        commands.nastavPrvni();
        boolean found = false;
        do {
            if (commands.dejAktualniNastroj() == n) {
                found = true;
                break;
            }
            commands.dalsi();
        } while (commands.jeDalsi());

        if (!found) {
            commands.nastavPrvni();
        }

        if (commands.dejAktualniNastroj() == commands.dejPrvni() && id != 0) {
            commands.nastavPosledni();
        }
        updateListView();
    }


    @FXML
    public void odebratId() throws OvladaniException {
        int id = pozadavekNaCislo ("Odebrat nástroj podle ID", "Zadejte ID nástroje, který chcete odebrat", "ID nástroje:");
        commands.odeberNastroj(id);
        commands.nastavPrvni();
        updateListView();
    }

    private Integer pozadavekNaCislo(String title, String headerText, String contentText) {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(contentText);

            String result = dialog.showAndWait().orElse(null);
            try {
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                alert("Chyba", "Chybný vstup", "Zadejte číslo");
            }
        }
    }


    public void alert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
