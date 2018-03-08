package contohlistview;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContohListViewController implements Initializable {
    @FXML
    private TextField txtNama;
    @FXML
    private Button btnSimpan;
    @FXML
    private ListView<String> listNama;
    @FXML
    private AnchorPane pane;

    private boolean editMode = false;
    private int editIndex = -1;

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
        // atau buat inner class dan kemudian instantiate
        //Platform.runLater(new X());

        btnSimpan.setDefaultButton(true);
        listNama.setOnKeyPressed((event) -> this.onListViewKeyPressed(event));
        listNama.setOnMouseClicked((event) -> this.onListViewMouseClicked(event));

        // process event ketika event pertama kali di-capture (memberikan kesempatan kepada parent untuk meng-handle
        // event terlebih dahulu sebelum child)
        pane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // cancel edit jika user menekan tombol esc dan aplikasi dalam mode editMode
            if (event.getCode().equals(KeyCode.ESCAPE) && editMode) {
                cancelEdit();
            }
        });
    }

    // inner class
    /* private class X implements Runnable {
        public void run() {
            txtNama.requestFocus();
        }
    } */

    private void cancelEdit() {
        if (editMode) {
            disableEditMode();
            txtNama.clear();
            txtNama.requestFocus();
            messageBox("Edit", "Edit canceled");
        }
    }

    // event handler jika user meng-double click item di list view
    public void onListViewMouseClicked(MouseEvent event) {
        // double click
        if (event.getClickCount() == 2) {
            // jika ada yang terpilih, ke mode edit
            if (listNama.getSelectionModel().getSelectedIndex() >= 0) {
                // ke mode edit
                enableEditMode(listNama.getSelectionModel().getSelectedIndex());
                // copy data ke text field untuk di edit
                txtNama.setText(listNama.getSelectionModel().getSelectedItem());
                txtNama.requestFocus();
            }
        }
    }

    /**
     * mengaktifkan mode pengeditan
     * @param index nomor index item yang akan diedit
     */
    private void enableEditMode(int index) {
        this.editMode = true;
        this.editIndex = index;
    }

    /**
     * mengnonaktifkan mode edit
     */
    private void disableEditMode() {
        this.editMode = false;
        this.editIndex = -1;
    }

    /**
     * event handler ketika user menekan tombol di list view
     * @param event key event yang dicapture
     */
    public void onListViewKeyPressed(KeyEvent event) {
        // jika tombol DELETE/BACKSPACE hapus item
       if (event.getCode().equals(KeyCode.DELETE) || event.getCode().equals(KeyCode.BACK_SPACE)) {
           int index = listNama.getSelectionModel().getSelectedIndex();
           if (index >= 0) {
               String data = listNama.getItems().get(index);
               // tampilkan form konfirmasi
               if (confirm("Hapus", "Yakin hapus " + data + "?")
                       .orElse(ButtonType.CANCEL).equals(ButtonType.OK)) {
                   // hapus
                   deleteItem(index);
               }
           }
       }
    }

    // dialog box untuk menampilkan pesan konfirmasi (OK, CANCEL)
    private Optional<ButtonType> confirm(String title, String message) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setContentText(message);
        Optional<ButtonType> result = confirm.showAndWait();
        return result;
    }

    /**
     * menghapus item
     * @param index nomor index item yang akan dihapus
     */
    private void deleteItem(int index) {
        if (index >= 0 && index < listNama.getItems().size()) {
            listNama.getItems().remove(index);
        }
    }

    /**
     * menambahkan item baru
     * @param newItem item baru
     */
    private void addItem(String newItem) {
        listNama.getItems().add(newItem);
    }

    /**
     * mengupdate item
     * @param index posisi item yang akan diupdate
     * @param updatedItem item baru
     */
    private void updateItem(int index, String updatedItem) {
        listNama.getItems().set(index, updatedItem);
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
            if (editMode) {
                //update
                updateItem(editIndex, txtNama.getText());
                disableEditMode();
            } else {
                // tambah
                addItem(txtNama.getText());
            }
            txtNama.clear();
            txtNama.requestFocus();
        } else {
            // tampilkan pesan bahwa nama harus diisi
            messageBox("Warning", "Nama tidak boleh kosong");
        }
    }

    /**
     * menampilkan dialog message box
     * @param title title dialog box
     * @param message pesan
     */
    public void messageBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
