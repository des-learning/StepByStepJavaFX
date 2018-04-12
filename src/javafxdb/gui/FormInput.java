package javafxdb.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxdb.model.Mahasiswa;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FormInput implements Initializable {
    private Stage stage;

    @FXML
    TextField txtNIM;
    @FXML
    TextField txtNama;
    @FXML
    Button btnSimpanMhs;

    private Mahasiswa mahasiswa = null;

    // setup form
    // baca form dari fxml
    // set controller
    // siapkan stage dan scene
    private void setup() throws IOException {
        stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormInput.fxml"));
        loader.setController(this);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    // pasang event handler
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            txtNIM.requestFocus();
        });

        // apabila tombol simpan ditekan, bentuk object mahasiswa dan tutup form ini
        btnSimpanMhs.setOnAction(event -> {
            this.mahasiswa = new Mahasiswa(txtNIM.getText(), txtNama.getText());
            this.stage.close();
        });
    }

    // tampilkan form dan tunggu action dari user
    public Optional<Mahasiswa> showAndWait(Stage stage) throws IOException {
        this.setup();

        // stage sebagai window induk
        this.stage.initOwner(stage);
        // set window modal (tidak bisa berinteraksi dengan form lain sebelum form ini ditutup)
        this.stage.initModality(Modality.APPLICATION_MODAL);
        // tampilkan
        this.stage.showAndWait();

        // kembalikan optional mahasiswa, jika ditutup melalui tombol btnSimpanMhs,
        // atribut mahasiswa dari form ini seharusnya sudah diisikan dengan object mahasiswa
        // apabila belum hasil dari form ini adalah empty
        return Optional.ofNullable(this.mahasiswa);
    }
}
