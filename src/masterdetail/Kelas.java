package masterdetail;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Kelas {
    private StringProperty nama;
    private StringProperty jurusan;
    private StringProperty fakultas;
    // detail
    private ListProperty<Mahasiswa> mahasiswa;

    public Kelas(String nama, String jurusan, String fakultas) {
        this.nama = new SimpleStringProperty(nama);
        this.jurusan = new SimpleStringProperty(jurusan);
        this.fakultas = new SimpleStringProperty(fakultas);

        this.mahasiswa = new SimpleListProperty<>(FXCollections.observableArrayList());
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

    public String getJurusan() {
        return jurusan.get();
    }

    public StringProperty jurusanProperty() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan.set(jurusan);
    }

    public String getFakultas() {
        return fakultas.get();
    }

    public StringProperty fakultasProperty() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas.set(fakultas);
    }

    public ObservableList<Mahasiswa> getMahasiswa() {
        return mahasiswa.get();
    }

    public ListProperty<Mahasiswa> mahasiswaProperty() {
        return mahasiswa;
    }

    public void setMahasiswa(ObservableList<Mahasiswa> mahasiswa) {
        this.mahasiswa.set(mahasiswa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kelas)) return false;
        Kelas kelas = (Kelas) o;
        return Objects.equals(getNama(), kelas.getNama()) &&
                Objects.equals(getJurusan(), kelas.getJurusan()) &&
                Objects.equals(getFakultas(), kelas.getFakultas()) &&
                Objects.equals(getMahasiswa(), kelas.getMahasiswa());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNama(), getJurusan(), getFakultas(), getMahasiswa());
    }

    @Override
    public String toString() {
        return getNama() + " (" + getJurusan() + ")";
    }
}
