package contohlistview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ContohListViewController implements Initializable {
    @FXML
    private TextField txtNama;
    @FXML
    private Button btnSimpan;
    @FXML
    private ListView<String> listNama;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listNama.setPlaceholder(new Label("Belum ada data"));

        // focus ke txtNama pada saat scene ditampilkan
        // karena proses pembuatan control bisa selesai pada waktu yang
        // tidak tetap, jalankan proses berikut pada waktu akan datang
        // penulisan kode berikut menggunakan syntax lambda pada java 8
        Platform.runLater(() -> {
            txtNama.requestFocus();
        });

        // bisa disingkat menjadi lambda expression (karena cuma 1 baris
        // expression, tidak boleh dibungkus dengan { } dan tidak boleh pakai ;
        // Platform.runLater(() -> txtNama.requestFocus());

        // atau bisa disingkat dengan mengirimkan method reference, karena
        // isi lambda cuma akan menjalankan method requestFocus dari txtNama
        //Platform.runLater(txtNama::requestFocus);

        // code jika menggunakan java <= 7 yang belum memiliki lambda
        /* Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtNama.requestFocus();
            }
        }); */
    }

    /**
     * menambahkan item ke listNama
     * @param event action event tombol btnSimpan
     */
    @FXML
    public void onBtnSimpanClick(ActionEvent event) {
        // baca nama dari txtNama, bersihkan whitespace (trim)
        String nama = txtNama.getText().trim();

        // jika tidak kosong, tambahkan nama ke listNama
        if (! nama.isEmpty()) {
            listNama.getItems().add(txtNama.getText());
            txtNama.clear();
            txtNama.requestFocus();
        } else {
            // tampilkan pesan bahwa nama harus diisi
            messageBox("Warning", "Nama tidak boleh kosong").showAndWait();
        }
    }

    public Alert messageBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert;
    }
}
