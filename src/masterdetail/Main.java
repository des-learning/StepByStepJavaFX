package masterdetail;

import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // model
    private ListProperty<Kelas> model;

    @Override
    public void start(Stage stage) throws Exception {
        // inisialisasi model
        model = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.add(new Kelas("17IS2",
                "Sistem Informasi", "Ilmu Komputer"));

        // buat controller & hubungkan model dengan controller
        FormKelasController controller = new FormKelasController(model);

        // load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormKelas.fxml"));
        // hubungkan view dengan controller
        loader.setController(controller);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String []args) {
        launch();
    }
}
