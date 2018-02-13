package contohtableview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ContohTableViewController implements Initializable {
    @FXML
    TextField txtNama;
    @FXML
    TextField txtUmur;
    @FXML
    Button btnSimpan;
    @FXML
    TableView<Manusia> tableManusia;
    @FXML
    TableColumn<Manusia, String> columNama;
    @FXML
    TableColumn<Manusia, Integer> columnUmur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(txtNama::requestFocus);

        // table property
        columNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        columnUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));
    }

    public void onBtnSimpanClick(ActionEvent event) {
        String nama = txtNama.getText().trim();
        Integer umur = 0;
        try {
            umur = Integer.valueOf(txtUmur.getText());
        } catch (NumberFormatException e) {
            messageBox("Contoh Table View", "Umur salah").showAndWait();
            txtUmur.selectAll();
            txtUmur.requestFocus();
            return;
        }
        txtNama.clear();
        txtUmur.clear();

        tableManusia.getItems().add(new Manusia(nama, umur));

        txtNama.requestFocus();
    }

    private Alert messageBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }
}
