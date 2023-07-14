package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.KlavesovyNastroj;
import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DialogKlavesovyNastroj extends ZakladniDialog {
    @FXML
    private TextField nazevTextField;

    @FXML
    private TextField pocetKlavesTextField;

    @FXML
    private Button potvrditButton;
    private Nastroj nastroj;
    public Nastroj getNovyNastroj() {
        return nastroj;
    }
    public DialogKlavesovyNastroj() {
        super("/dialogyFXML/dialogKlavesovyNastroj.fxml");
        stage.setTitle("Dialog klávesový nástroj");
        potvrditButton.setOnAction(event -> potvrdit());
    }

    public DialogKlavesovyNastroj(Nastroj nastrojToEdit) {
        super("/dialogyFXML/dialogKlavesovyNastroj.fxml");
        stage.setTitle("Editor klávesového nástroje");
        this.nastroj = nastrojToEdit;
        if (nastroj != null) {
            nazevTextField.setText(nastroj.getNazev());
            pocetKlavesTextField.setText(String.valueOf(((KlavesovyNastroj) nastroj).getPocetKlaves()));
        }
        potvrditButton.setOnAction(event -> potvrdit());
    }

    private void potvrdit() {
        String nazev = nazevTextField.getText();
        int pocetKlaves;
        try {
            pocetKlaves = Integer.parseInt(pocetKlavesTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Chyba", "Počet kláves musí být číslo.");
            return;
        }

        if (nazev == null || nazev.isEmpty() || pocetKlaves < 0) {
            showAlert("Chyba", "Vyplňte všechna pole.");
            return;
        }

        if (nastroj == null) {
            nastroj = new KlavesovyNastroj(nazev, NastrojTyp.KLAVESOVA, pocetKlaves);
        } else {
            nastroj.setNazev(nazev);
            ((KlavesovyNastroj) nastroj).setPocetKlaves(pocetKlaves);
        }
        stage.close();
    }
}
