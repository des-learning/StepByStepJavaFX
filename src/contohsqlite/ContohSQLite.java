package contohsqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContohSQLite {
    public static Connection connectAndCreateTable(String url) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();

            String sql = "create table if not exists Person (" +
                            "id integer PRIMARY KEY, " +
                            "name varchar(255) not null, " +
                            "gender char(1) default 'm'" +
                            ")";

            stmt.execute(sql);

        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void closeDatabase(Connection conn) {
        try {
            conn.close();
            System.out.println("Database closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(Statement stmt, String name, char gender) {
        try {
            // insert ke database tanpa primary key
            String sql = "Insert Into Person values (null, '" + name + "', '" + gender + "')";
            stmt.execute(sql);
            System.out.println("Data " + name + "(" + gender + ") inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet queryData(Statement stmt, String sql) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ResultSet queryUsingPreparedStatement(Connection conn, String name) {
        ResultSet result = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select * from Person where name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            result = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void updateName(Connection conn, String oldName, String newName) {
        try {
            String sql = "update Person set name = ? where name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newName);
            stmt.setString(2, oldName);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteByGender(Connection conn, Character gender) {
        try {
            String sql = "delete from Person where gender = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, gender.toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayResult(ResultSet result) {
        if (result != null) {
            try {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    char gender = result.getString("gender").charAt(0);
                    System.out.printf("%2d %20s %c\n", id, name, gender);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String []args) {
        Connection conn = connectAndCreateTable("jdbc:sqlite:./testing.sqlite");
        Statement stmt = null;
        if (conn != null) {
            try {
                stmt = conn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (stmt != null) {
                // insert data to table Person
                insertData(stmt, "Rudy", 'm');
                insertData(stmt, "Susy", 'f');

                // query ke database
                ResultSet result = queryData(stmt, "select * from Person");
                System.out.println("After query");
                displayResult(result);

                // prepared statement
                result = queryUsingPreparedStatement(conn, "Rudy");
                System.out.println("After prepared statement");
                displayResult(result);

                // update
                updateName(conn, "Rudy", "Andi");
                result = queryData(stmt, "select * from Person");
                System.out.println("After update");
                displayResult(result);

                // delete
                deleteByGender(conn, 'm');
                result = queryData(stmt, "select * from Person");
                System.out.println("After delete");
                displayResult(result);
            }

            // close database
            closeDatabase(conn);
        }
    }
}
