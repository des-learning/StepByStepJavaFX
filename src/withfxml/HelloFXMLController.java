package withfxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloFXMLController implements Initializable {
    @FXML
    TextField txtNama;
    @FXML
    Button btnHello;
    @FXML
    Label lblHello;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onBtnHelloClick(ActionEvent event) {
        lblHello.setText("Hello " + txtNama.getText());
    }
}
