package masterdetail;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FormKelasController implements Initializable {
    private ListProperty<Kelas> model;

    @FXML
    private TextField txtKelas;
    @FXML
    private TextField txtJurusan;
    @FXML
    private TextField txtFakultas;
    @FXML
    private TextField txtNama;
    @FXML
    private TextField txtNIM;
    @FXML
    private Button btnSimpanMhs;
    @FXML
    private Button btnSimpan;
    @FXML
    private ListView<Kelas> lstKelas;
    @FXML
    private TableView<Mahasiswa> tblMahasiswa;
    @FXML
    private TableColumn<Mahasiswa, String> columnNIM;
    @FXML
    private TableColumn<Mahasiswa, String> columnNama;

    // model
    private Kelas data;
    private Mahasiswa dataMhs;

    // false - add new, true - edit
    private boolean mode;


    public FormKelasController(ListProperty<Kelas> model) {
        this.model = model;
        this.mode = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // sambungkan model ke lstKelas
        lstKelas.itemsProperty().bindBidirectional(model);
        lstKelas.setCellFactory(param -> new TampilanListKelas());

        // bind model baru untuk input
        this.bindNewModel();

        // logic untuk double click list kelas, ke mode edit
        lstKelas.setOnMouseClicked(this::editKelas);

        // logic untuk tombol simpan mahasiswa
        btnSimpanMhs.setOnAction(this::simpanMahasiswa);

        // logic untuk tombol simpan
        btnSimpan.setOnAction(this::simpanKelas);

        // setting view list mahasiswa
        columnNIM.setCellValueFactory(new PropertyValueFactory<>("NIM"));
        columnNama.setCellValueFactory(new PropertyValueFactory<>("Nama"));
    }

    public void bindNewModel() {
        unbindInput();
        this.data = new Kelas("", "", "");
        this.dataMhs = new Mahasiswa("", "");
        this.bindEditModel();
    }

    public void unbindInput() {
        if (this.data != null) {
            txtKelas.textProperty().unbindBidirectional(this.data.namaProperty());
            txtFakultas.textProperty().unbindBidirectional(this.data.fakultasProperty());
            txtJurusan.textProperty().unbindBidirectional(this.data.jurusanProperty());
            tblMahasiswa.itemsProperty().unbindBidirectional(this.data.mahasiswaProperty());
        }
    }

    public void bindEditModel() {
        // sambungkan model kelas dengan input
        txtKelas.textProperty().bindBidirectional(data.namaProperty());
        txtJurusan.textProperty().bindBidirectional(data.jurusanProperty());
        txtFakultas.textProperty().bindBidirectional(data.fakultasProperty());
        tblMahasiswa.itemsProperty().bindBidirectional(data.mahasiswaProperty());

        // sambungkan model dengan input
        this.bindModelMhs();
    }

    public void bindNewModelMhs() {
        unbindModelMhs();
        this.dataMhs = new Mahasiswa("", "");
        bindModelMhs();
    }

    public void bindModelMhs () {
        txtNIM.textProperty().bindBidirectional(dataMhs.NIMProperty());
        txtNama.textProperty().bindBidirectional(dataMhs.namaProperty());
    }

    public void unbindModelMhs () {
        txtNIM.textProperty().unbindBidirectional(dataMhs.NIMProperty());
        txtNama.textProperty().unbindBidirectional(dataMhs.namaProperty());
    }

    public void simpanMahasiswa(ActionEvent event) {
        // tambahkan data mhs ke list mahasiswa nya kelas
        data.mahasiswaProperty().add(new Mahasiswa(this.dataMhs.getNIM(), this.dataMhs.getNama()));
        bindNewModelMhs();
    }

    public void simpanKelas(ActionEvent event) {
        if (!mode) {
            Kelas newKelas = new Kelas(this.data.getNama(),
                    this.data.getJurusan(), this.data.getFakultas());
            newKelas.mahasiswaProperty().setValue(this.data.mahasiswaProperty());
            model.add(newKelas);
        } else {
            mode = false;
        }
        this.bindNewModel();
        txtFakultas.requestFocus();
    }

    public void editKelas(MouseEvent event) {
        // double click
        if (event.getClickCount() == 2) {
            ListView<Kelas> target = (ListView) event.getSource();
            // ambil data yang dipilih simpan ke model data, masuk ke mode edit
            if (target.getSelectionModel().getSelectedItem() != null) {
                this.data = target.getSelectionModel().getSelectedItem();
                mode = true;
                this.bindEditModel();
            }
        }
    }
}

class TampilanListKelas extends ListCell<Kelas> {
    @Override
    protected void updateItem(Kelas item, boolean empty) {
        super.updateItem(item, empty);
        textProperty().unbind();
        if (empty || item == null) {
            setText(null);
        } else {
            textProperty().bind(
                    Bindings.concat(item.namaProperty())
                            .concat(" (").concat(item.jurusanProperty())
                            .concat(")").concat(" : ")
            .concat(item.mahasiswaProperty().sizeProperty()));
        }
    }
}
