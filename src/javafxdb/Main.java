package javafxdb;

import javafxdb.gui.MainGUI;

import java.sql.SQLException;

public class Main {
    public static void main(String []args) throws SQLException {
        // launch gui app
        MainGUI gui = new MainGUI();
        gui.start();
    }
}
