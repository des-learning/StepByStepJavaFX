package contohpropertybinding;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContohPropertyBinding extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();

        TextField txtNama = new TextField();
        TextField txtUmur = new TextField();

        Manusia manusia = new Manusia("Budi", 20, true);

        // bind manusia ke text field, binding dua arah
        // apabila manusia diubah otomatis nilai di text field berubah
        // dan apabila nilai di text field diubah, object manusia akan ikut berubah
        txtNama.textProperty().bindBidirectional(manusia.namaProperty());
        txtUmur.textProperty().bindBidirectional(manusia.umurProperty(), new IntegerStringConverter());

        Label output = new Label();

        // expression yang dibinding ke property, untuk menghasilkan nilai dinamis
        // contoh: Budi, 20 tahun
        StringExpression panggilan = Bindings.when(
                manusia.jenisKelaminProperty().isEqualTo(new SimpleBooleanProperty(true)))
                .then("Bapak").otherwise("Ibu");
        StringExpression expr =
                panggilan.concat(" ").concat(
                manusia.namaProperty().concat(
                new SimpleStringProperty(", ")
                        .concat(manusia.umurProperty().asString()
                                .concat(new SimpleStringProperty(" tahun")))));

        // bind isi label ke string expression
        output.textProperty().bind(expr);

        // tambahkan control ke container
        root.getChildren().add(txtNama);
        root.getChildren().add(txtUmur);
        root.getChildren().add(output);

        root.setSpacing(10.0);
        root.setPadding(new Insets(10, 10, 10, 10));

        Button btn = new Button("Default");
        btn.setDefaultButton(true);

        CheckBox chkJenisKelamin = new CheckBox();
        chkJenisKelamin.selectedProperty().bindBidirectional(manusia.jenisKelaminProperty());

        root.getChildren().add(chkJenisKelamin);

        // mengembalikan manusia ke default value
        btn.setOnAction(event -> {
            manusia.namaProperty().setValue("Budi");
            manusia.umurProperty().setValue(20);
            manusia.setJenisKelamin(true);
        });

        root.getChildren().add(btn);

        Scene scene = new Scene(root);
        stage.setTitle("Contoh Property dan Binding");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String []args) {
        launch();
    }
}
