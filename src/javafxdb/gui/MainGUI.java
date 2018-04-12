package javafxdb.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxdb.model.MahasiswaDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainGUI extends Application {
    // buka koneksi ke database, bentuk table dan isikan dengan data default apabila
    // table masih kosong
    public Connection connectAndCreateTable(String url) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        String sql = "create table if not exists Mahasiswa (" +
                "NIM varchar(11) PRIMARY KEY, " +
                "nama varchar(255) not null" +
                ")";
        stmt.execute(sql);

        ResultSet resultSet = stmt.executeQuery("select * from Mahasiswa");

        // jika belum ada data tambahkan data contoh
        if (! resultSet.next()) {
            stmt.execute("insert into Mahasiswa values (\"00001\", \"Budi\")");
            stmt.execute("insert into Mahasiswa values (\"00002\", \"Susi\")");
        }
        return conn;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // inisialisasi database
        Connection conn = connectAndCreateTable("jdbc:sqlite:mahasiswa.sqlite");
        MahasiswaDao dao = new MahasiswaDao(conn);

        // inisialisasi gui
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FormList.fxml"));
        // kirimkan dao untuk digunakan di gui
        Initializable controller = new MainGUIController(dao, stage);
        // set controller gui
        loader.setController(controller);

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void start() {
        launch();
    }
}
