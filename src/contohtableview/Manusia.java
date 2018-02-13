package contohtableview;

import java.util.Objects;

public class Manusia {
    private String nama;
    private Integer umur;

    public Manusia(String nama, Integer umur) {
        this.nama = nama;
        this.umur = umur;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
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
                "nama='" + nama + '\'' +
                ", umur=" + umur +
                '}';
    }
}
