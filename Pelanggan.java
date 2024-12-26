package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pelanggan {

    private final IntegerProperty id;
    private final StringProperty nama;
    private final StringProperty email;
    private final StringProperty telepon;  // Properti telepon ditambahkan

    public Pelanggan(int id, String nama, String email, String telepon) {
        this.id = new SimpleIntegerProperty(id);
        this.nama = new SimpleStringProperty(nama);
        this.email = new SimpleStringProperty(email);
        this.telepon = new SimpleStringProperty(telepon);  // Inisialisasi telepon
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty teleponProperty() {  // Getter untuk telepon
        return telepon;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getTelepon() {  // Getter untuk telepon
        return telepon.get();
    }

    public void setTelepon(String telepon) {  // Setter untuk telepon
        this.telepon.set(telepon);
    }
}