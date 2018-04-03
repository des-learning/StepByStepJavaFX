package javafxdb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MahasiswaDao {
    private Connection conn;
    private PreparedStatement stmt;

    public MahasiswaDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * menambahkan mahasiswa ke database
     * @param mhs data mahasiswa yang ingin ditambahkan
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan penambahan data
     */
    public boolean add(Mahasiswa mhs) throws SQLException {
       String sql = "INSERT INTO Mahasiswa VALUES (?, ?)";
       stmt = conn.prepareStatement(sql);
       stmt.setString(1, mhs.getNIM());
       stmt.setString(2, mhs.getNama());

       return stmt.execute();
    }

    /**
     * menambahkan mahasiswa ke database
     * @param NIM nim mahasiswa
     * @param nama nama mahasiswa
     * @return mengambalikan true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan penambahan data
     */
    public boolean add(String NIM, String nama) throws SQLException {
        return add(new Mahasiswa(NIM, nama));
    }

    /**
     * mengupdate data mahasiswa
     * @param NIM nim data mahasiswa yang akan diupdate
     * @param mhs data mahasiswa yang akan diupdate
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean update(String NIM, Mahasiswa mhs) throws SQLException {
        String sql = "UPDATE Mahasiswa SET nama=? where NIM=?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, mhs.getNama());
        stmt.setString(2, NIM);

        return stmt.execute();
    }

    /**
     * mengupdate data mhs
     * @param mhs data mahasiswa yang akan di-update
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kesalahan pada operasi sql
     */
    public boolean update(Mahasiswa mhs) throws SQLException {
        return update(mhs.getNIM(), mhs);
    }

    /**
     * menghapus data mahasiswa
     * @param NIM nim mahasiswa yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean delete(String NIM) throws SQLException {
        String sql = "DELETE FROM Mahasiswa WHERE NIM=?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, NIM);

        return stmt.execute();
    }

    /**
     * menghapus data mahasiswa
     * @param mhs mahasiswa yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public boolean delete(Mahasiswa mhs) throws SQLException {
        return delete(mhs.getNIM());
    }

    /**
     * mengakses seluruh data mahasiswa
     * @return list mahasiswa
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public List<Mahasiswa> all() throws SQLException {
        String sql = "SELECT * FROM Mahasiswa";
        stmt = conn.prepareStatement(sql);

        List<Mahasiswa> mhs = new ArrayList<>();
        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()) {
            mhs.add(new Mahasiswa(resultSet.getString("NIM"), resultSet.getString("nama")));
        }

        return mhs;
    }

    /**
     * mengakses data mahasiswa
     * @param NIM nim mahasiswa yang akan diakses
     * @return Optional Mahasiswa
     * @throws SQLException
     */
    public List<Mahasiswa> findByNIM(String NIM) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where NIM like ?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, "%" + NIM + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Mahasiswa> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Mahasiswa(resultSet.getString("NIM"), resultSet.getString("nama")));
        }

        return mhs;
    }

    /**
     * mengakses mahasiswa dengan nama tertentu %nama%
     * @param nama nama mahasiswa yang ingin dicari
     * @return list mahasiswa
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public List<Mahasiswa> findByNama(String nama) throws SQLException {
        String sql = "SELECT * FROM Mahasiswa where nama like ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nama + "%");

        ResultSet resultSet = stmt.executeQuery();

        List<Mahasiswa> mhs = new ArrayList<>();
        while (resultSet.next()) {
            mhs.add(new Mahasiswa(resultSet.getString("NIM"), resultSet.getString("nama")));
        }

        return mhs;
    }

    /**
     * menghitung banyak data
     * @return banyak data mahasiswa jika tidak ada hasil yang dikembalikan
     * @throws SQLException jika terjadi kegagalan operasi sql
     */
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Mahasiswa";
        stmt = conn.prepareStatement(sql);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return -1;
        }
    }
}
