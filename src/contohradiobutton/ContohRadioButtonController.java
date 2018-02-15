package contohradiobutton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class ContohRadioButtonController implements Initializable {
    @FXML
    private TextField txtNama;
    @FXML
    private RadioButton rdoPria;
    @FXML
    private RadioButton rdoWanita;
    @FXML
    private ToggleGroup tglJenisKelamin;
    @FXML
    private Label lblHello;
    @FXML
    private Button btnHello;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnHello.setOnAction(this::onHelloAction);
        rdoPria.setOnAction(this::onHelloAction);
        rdoWanita.setOnAction(this::onHelloAction);
    }

    private String getTitle() {
        if (tglJenisKelamin.getSelectedToggle().equals(rdoPria))
            return "Bapak";
        else
            return "Ibu";
    }

    @FXML
    private void onHelloAction(ActionEvent event) {
        lblHello.setText("Hello " + getTitle() + " " + txtNama.getText());
    }
}
