package contohpropertybinding;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

// javafx bean
public class Manusia {
    // pakai property untuk atribut
    private StringProperty nama;
    private IntegerProperty umur;

    public Manusia(String nama, Integer umur) {
        this.nama = new SimpleStringProperty(nama);
        this.umur = new SimpleIntegerProperty(umur);
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
