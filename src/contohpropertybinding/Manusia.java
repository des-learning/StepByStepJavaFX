package contohpropertybinding;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

// javafx bean
public class Manusia {
    // pakai property untuk atribut
    private StringProperty nama;
    private IntegerProperty umur;
    private BooleanProperty jenisKelamin;

    public Manusia(String nama, Integer umur, Boolean jenisKelamin) {
        this.nama = new SimpleStringProperty(nama);
        this.umur = new SimpleIntegerProperty(umur);
        this.jenisKelamin = new SimpleBooleanProperty(jenisKelamin);
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

    public int getUmur() {
        return umur.get();
    }

    public IntegerProperty umurProperty() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur.set(umur);
    }

    public Boolean getJenisKelamin() { return jenisKelamin.get(); }

    public void setJenisKelamin(boolean jenisKelamin) { this.jenisKelamin.set(jenisKelamin); }

    public BooleanProperty jenisKelaminProperty() { return jenisKelamin; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manusia manusia = (Manusia) o;
        return Objects.equals(nama, manusia.nama) &&
                Objects.equals(umur, manusia.umur);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nama, umur);
    }

    @Override
    public String toString() {
        return "Manusia{" +
                "nama=" + nama +
                ", umur=" + umur +
                '}';
    }
}
