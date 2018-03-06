package contohpropertybinding;

import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewWithPropertyBindingController implements Initializable {
    @FXML
    private TableView<Manusia> tableManusia;
    @FXML
    private TableColumn<Manusia, String> columnNama;
    @FXML
    private TableColumn<Manusia, Number> columnUmur;
    @FXML
    private TextField txtNama, txtUmur;
    @FXML
    private Button btnSimpan;
    @FXML
    private Label lblItems;
    private ListProperty<Manusia> listManusia;

    StringProperty nama;
    IntegerProperty umur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // listManusia menggunakan array list sebagai data backend
        listManusia = new SimpleListProperty<>(FXCollections.observableArrayList());

        // bind listManusia ke tableview
        tableManusia.itemsProperty().bindBidirectional(listManusia);

        // bind column ke atribut nama dan umur Manusia
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        columnUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));

        // binding textField
        nama = new SimpleStringProperty("");
        umur = new SimpleIntegerProperty(0);
        txtNama.textProperty().bindBidirectional(nama);
        txtUmur.textProperty().bindBidirectional(umur, new IntegerStringConverter());

        // jumlah items di listManusia
        StringExpression totalItems = listManusia.sizeProperty().asString();
        lblItems.textProperty().bind(totalItems);

        Platform.runLater(() -> {
            txtNama.requestFocus();
        });

        btnSimpan.setDefaultButton(true);
    }

    @FXML
    private void onBtnSimpanClick(ActionEvent event) {
        // buat manusia baru
        Manusia newManusia = new Manusia(nama.get(), umur.get());

        // tambahkan ke listManusia, otomatis tableView terupdate sesuai dengan isi list
        listManusia.add(newManusia);

        clearInput();
        txtNama.requestFocus();
    }

    private void clearInput() {
        this.txtNama.clear();
        this.txtUmur.clear();
    }
}
