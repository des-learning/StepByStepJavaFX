package javafxdb.gui;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafxdb.model.Mahasiswa;
import javafxdb.model.MahasiswaDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    @FXML
    private TableView<Mahasiswa> tableMhs;
    @FXML
    private TableColumn<Mahasiswa, String> columnNIM;
    @FXML
    private TableColumn<Mahasiswa, String> columnNama;
    @FXML
    private TextField txtSaring;
    @FXML
    private Button btnTambah;
    @FXML
    private Button btnSaring;

    @FXML
    private Button btnInput;

    @FXML
    private Label lblCurrentPage;

    @FXML
    private Label lblTotalPage;

    @FXML
    private Button btnNext;

    private MahasiswaDao dao;
    private ListProperty<Mahasiswa> mhs;
    private IntegerProperty currentPage = new SimpleIntegerProperty();
    private IntegerProperty totalPages = new SimpleIntegerProperty();
    private int itemsCount;
    private int itemsPerPage;

    String saring;
    // primary stage, untuk dikirim ke form input sebagai parent window
    Stage stage;

    public MainGUIController(MahasiswaDao dao, Stage stage) {
        this.dao = dao;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentPage.setValue(1);
        itemsPerPage = 2;
        // load semua data mahasiswa
        /* try {
            mhs.addAll(dao.all());

        } catch (SQLException e) {
            e.printStackTrace();
        } */
        mhs = new SimpleListProperty<>(FXCollections.observableArrayList());
        tableMhs.itemsProperty().bind(mhs);
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        columnNIM.setCellValueFactory(new PropertyValueFactory<>("NIM"));

        runSQL(() -> {
            itemsCount = dao.count();
            totalPages.setValue(Math.ceil(itemsCount *1.0 / itemsPerPage));
            mhs.addAll(dao.byPage(currentPage.getValue()-1, itemsPerPage));
        });

        lblCurrentPage.textProperty().bind(currentPage.asString());
        lblTotalPage.textProperty().bind(totalPages.asString());

        btnSaring.setOnAction(event -> {
            saring = txtSaring.getText();
            mhs.clear();
            try {
                // cari by nim
                Integer.parseInt(saring);
                runSQL(() -> mhs.addAll(dao.findByNIM(saring)));
            } catch (NumberFormatException e) {
                runSQL(() -> mhs.addAll(dao.findByNama(saring)));
            }
        });

        tableMhs.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                runSQL(() -> dao.delete(tableMhs.getSelectionModel().getSelectedItem()));
                mhs.clear();
                runSQL( () -> {
                    mhs.addAll(dao.byPage(currentPage.getValue()-1, itemsPerPage));
                });
            }
        });

        btnTambah.setOnAction(event -> {
            // buat dialog
            Mahasiswa m = inputDataMahasiswa(new Mahasiswa("", ""), false).orElse(null);
            if (m != null) {
                runSQL(() -> {
                    dao.add(m);
                    mhs.clear();
                    // mhs.addAll(dao.all());
                    mhs.addAll(dao.byPage(currentPage.getValue()-1, itemsPerPage));
                });
            }
        });

        tableMhs.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Mahasiswa m = inputDataMahasiswa(tableMhs.getSelectionModel().getSelectedItem(), true).orElse(null);
                if (m != null) {
                    runSQL(() -> {
                        dao.update(m);
                        mhs.clear();
                        //mhs.addAll(dao.all());
                        mhs.addAll(dao.byPage(currentPage.getValue()-1, itemsPerPage));
                    });
                }
            }
        });

        btnNext.setOnAction(event -> {
            if (currentPage.getValue() < totalPages.getValue()) {
                currentPage.setValue(currentPage.getValue() + 1);
                runSQL(() -> {
                    mhs.clear();
                    mhs.addAll(dao.byPage(currentPage.getValue() - 1, itemsPerPage));
                });
            }
        });

        btnInput.setOnAction(event -> {
            // create form input (with controller)
            FormInput formInput = new FormInput();
            try {
                // tampilkan form input dan tunggu sampai form ditutup (sudah ada hasil)
                // hasil dari form ini adalah optional, ada data mahasiswa yang diinput (apabila user menekan tombol
                // simpan atau empty apabila form ditutup
                Optional<Mahasiswa> optionalMahasiswa = formInput.showAndWait(this.stage);
                // jika ada data mahasiswa
                optionalMahasiswa.ifPresent(m -> {
                    // tambahkan ke database
                    runSQL(() -> {
                        dao.add(m);
                        mhs.clear();
                        mhs.addAll(dao.byPage(currentPage.getValue() - 1, itemsPerPage));
                    });
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Optional<Mahasiswa> inputDataMahasiswa(Mahasiswa mhs, boolean edit) {
        Dialog<Mahasiswa> dialog = new Dialog<>();
        dialog.setTitle("Input Data Mahasiswa");

        // Set the button types.
        ButtonType okButtonType = new ButtonType("Simpan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nim = new TextField();
        nim.setPromptText("NIM");
        nim.setText(mhs.getNIM());
        if (edit) nim.setDisable(true);
        TextField nama = new TextField();
        nama.setPromptText("Nama");
        nama.setText(mhs.getNama());

        grid.add(new Label("NIM:"), 0, 0);
        grid.add(nim, 1, 0);
        grid.add(new Label("Nama:"), 0, 1);
        grid.add(nama, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(nim::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Mahasiswa(nim.getText(), nama.getText());
            }
            return null;
        });

        Optional<Mahasiswa> result = dialog.showAndWait();
        return result;
    }

    private void runSQL(SQLAction action) {
        try {
            action.run();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

@FunctionalInterface
interface SQLAction {
    void run() throws SQLException;
}
