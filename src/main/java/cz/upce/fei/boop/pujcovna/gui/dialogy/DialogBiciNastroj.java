package cz.upce.fei.boop.pujcovna.gui.dialogy;

import cz.upce.fei.boop.pujcovna.data.BiciNastroj;
import cz.upce.fei.boop.pujcovna.data.Nastroj;
import cz.upce.fei.boop.pujcovna.data.NastrojTyp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DialogBiciNastroj extends ZakladniDialog {
    @FXML
    private TextField materialTextField;

    @FXML
    private TextField nazevTextField;
    @FXML
    private Button potvrditButton;
    private Nastroj nastroj;
    public Nastroj getNovyNastroj() {
        return nastroj;
    }
    public DialogBiciNastroj() {
        super("/dialogyFXML/dialogBiciNastroj.fxml");
        stage.setTitle("Dialog bicí nástroj");
        potvrditButton.setOnAction(event -> potvrdit());
    }

    public DialogBiciNastroj(Nastroj nastrojToEdit) {
        super("/dialogyFXML/dialogBiciNastroj.fxml");
        stage.setTitle("Editor bicího nástroje");
        this.nastroj = nastrojToEdit;
        if (nastroj != null) {
            nazevTextField.setText(nastroj.getNazev());
            materialTextField.setText(((BiciNastroj) nastroj).getMaterial());
        }
        potvrditButton.setOnAction(event -> potvrdit());
    }

    private void potvrdit() {
        String nazev = nazevTextField.getText();
        String material = materialTextField.getText();

        if (material == null || nazev == null || nazev.isEmpty() || material.isEmpty()) {
            showAlert("Chyba", "Vyplňte všechna pole.");
            return;
        }

        if (nastroj == null) {
            nastroj = new BiciNastroj(nazev, NastrojTyp.BICI, material);
        } else {
            nastroj.setNazev(nazev);
            ((BiciNastroj) nastroj).setMaterial(material);
        }
        stage.close();
    }
}

