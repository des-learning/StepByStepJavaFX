package masterdetail;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Mahasiswa {
    private StringProperty NIM;
    private StringProperty nama;

    public Mahasiswa(String NIM, String nama) {
        this.NIM = new SimpleStringProperty(NIM);
        this.nama = new SimpleStringProperty(nama);
    }

    public String getNIM() {
        return NIM.get();
    }

    public StringProperty NIMProperty() {
        return NIM;
    }

    public void setNIM(String NIM) {
        this.NIM.set(NIM);
    }

    public String getNama() {
        return nama.get();
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mahasiswa)) return false;
        Mahasiswa mahasiswa = (Mahasiswa) o;
        return Objects.equals(getNIM(), mahasiswa.getNIM()) &&
                Objects.equals(getNama(), mahasiswa.getNama());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNIM(), getNama());
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "NIM=" + NIM +
                ", nama=" + nama +
                '}';
    }
}
