package model;

import javafx.beans.property.*;

public class Buku {
    private IntegerProperty bukuid;
    private StringProperty judul;
    private StringProperty penulis;
    private DoubleProperty harga;
    private IntegerProperty stok;

    public Buku(int bukuid, String judul, String penulis, double harga, int stok) {
        this.bukuid = new SimpleIntegerProperty(bukuid);
        this.judul = new SimpleStringProperty(judul);
        this.penulis = new SimpleStringProperty(penulis);
        this.harga = new SimpleDoubleProperty(harga);
        this.stok = new SimpleIntegerProperty(stok);
    }

    // Getter dan Setter untuk bukuid
    public int getBukuid() {
        return bukuid.get();
    }

    public void setBukuid(int bukuid) {
        this.bukuid.set(bukuid);
    }

    public IntegerProperty bukuidProperty() {
        return bukuid;
    }

    // Getter dan Setter untuk judul
    public String getJudul() {
        return judul.get();
    }

    public void setJudul(String judul) {
        this.judul.set(judul);
    }

    public StringProperty judulProperty() {
        return judul;
    }

    // Getter dan Setter untuk penulis
    public String getPenulis() {
        return penulis.get();
    }

    public void setPenulis(String penulis) {
        this.penulis.set(penulis);
    }

    public StringProperty penulisProperty() {
        return penulis;
    }

    // Getter dan Setter untuk harga
    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double harga) {
        this.harga.set(harga);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }

    // Getter dan Setter untuk stok
    public int getStok() {
        return stok.get();
    }

    public void setStok(int stok) {
        this.stok.set(stok);
    }

    public IntegerProperty stokProperty() {
        return stok;
    }
}