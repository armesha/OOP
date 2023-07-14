package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;
import cz.upce.fei.boop.pujcovna.data.SmyccovyNastroj;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DialogSmyccovyNastroj extends ZakladniDialog {
    @FXML
    private TextField nazevTextField;

    @FXML
    private TextField pocetStrunTextField;
    @FXML
    private Button potvrditButton;
    private Nastroj nastroj;
    public Nastroj getNovyNastroj() {
        return nastroj;
    }
    public DialogSmyccovyNastroj() {
        super("/dialogyFXML/dialogSmyccovyNastroj.fxml");
        stage.setTitle("Dialog smyčcový nástroj");
        potvrditButton.setOnAction(event -> potvrdit());
    }

    public DialogSmyccovyNastroj(Nastroj nastrojToEdit) {
        super("/dialogyFXML/dialogSmyccovyNastroj.fxml");
        stage.setTitle("Editor smyčcového nástroje");
        this.nastroj = nastrojToEdit;
        if (nastroj != null) {
            nazevTextField.setText(nastroj.getNazev());
            pocetStrunTextField.setText(String.valueOf(((SmyccovyNastroj) nastroj).getPocetStrun()));
        }
        potvrditButton.setOnAction(event -> potvrdit());
    }

    private void potvrdit() {
        String nazev = nazevTextField.getText();
        int pocetStrun;
        try {
            pocetStrun = Integer.parseInt(pocetStrunTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Chyba", "Počet strun musí být číslo.");
            return;
        }

        if (nazev == null || nazev.isEmpty() || pocetStrun < 0) {
            showAlert("Chyba", "Vyplňte všechna pole.");
            return;
        }

        if (nastroj == null) {
            nastroj = new SmyccovyNastroj(nazev, NastrojTyp.SMYCCOVA, pocetStrun);
        } else {
            nastroj.setNazev(nazev);
            ((SmyccovyNastroj) nastroj).setPocetStrun(pocetStrun);
        }
        stage.close();
    }
}
