package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import controller.DatabaseHelper;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Penjualan {
    private IntegerProperty id;
    private IntegerProperty jumlah;
    private DoubleProperty totalHarga;
    private StringProperty tanggal;
    private IntegerProperty pelangganID;

    public Penjualan() {
        this.id = new SimpleIntegerProperty();
        this.jumlah = new SimpleIntegerProperty();
        this.totalHarga = new SimpleDoubleProperty();
        this.tanggal = new SimpleStringProperty();
        this.pelangganID = new SimpleIntegerProperty();
    }

    public Penjualan(int id, int jumlah, double totalHarga, String tanggal, int pelangganID) {
        this.id = new SimpleIntegerProperty(id);
        this.jumlah = new SimpleIntegerProperty(jumlah);
        this.totalHarga = new SimpleDoubleProperty(totalHarga);
        this.tanggal = new SimpleStringProperty(tanggal);
        this.pelangganID = new SimpleIntegerProperty(pelangganID);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getJumlah() {
        return jumlah.get();
    }

    public IntegerProperty jumlahProperty() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah.set(jumlah);
    }

    public double getTotalHarga() {
        return totalHarga.get();
    }

    public DoubleProperty totalHargaProperty() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga.set(totalHarga);
    }

    public String getTanggal() {
        return tanggal.get();
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal.set(tanggal);
    }

    public int getPelangganID() {
        return pelangganID.get();
    }

    public IntegerProperty pelangganIDProperty() {
        return pelangganID;
    }

    public void setPelangganID(int pelangganID) {
        this.pelangganID.set(pelangganID);
    }

    // Load all Penjualan from database
    public static ObservableList<Penjualan> loadAllPenjualan() {
        ObservableList<Penjualan> allPenjualan = FXCollections.observableArrayList();
        String query = "SELECT * FROM penjualan";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Penjualan penjualan = new Penjualan();
                penjualan.setId(rs.getInt("penjualan_id"));
                penjualan.setJumlah(rs.getInt("jumlah"));
                penjualan.setTotalHarga(rs.getDouble("total_harga"));
                penjualan.setTanggal(rs.getString("tanggal"));
                penjualan.setPelangganID(rs.getInt("pelanggan_id"));
                allPenjualan.add(penjualan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPenjualan;
    }

    // Load filtered Penjualan based on pelanggan, buku, and tanggal
    public static ObservableList<Penjualan> loadFilteredPenjualan(Pelanggan pelanggan, Buku buku, String tanggal) {
        ObservableList<Penjualan> filteredPenjualan = FXCollections.observableArrayList();

        StringBuilder query = new StringBuilder("SELECT * FROM penjualan WHERE 1=1");

        if (pelanggan != null) {
            query.append(" AND pelanggan_id = ?");
        }

        if (buku != null) {
            query.append(" AND buku_id = ?");
        }

        if (tanggal != null) {
            query.append(" AND tanggal = ?");
        }

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (pelanggan != null) {
                stmt.setInt(index++, pelanggan.getId());
            }
            if (buku != null) {
                stmt.setInt(index++, buku.getBukuid());
            }
            if (tanggal != null) {
                stmt.setString(index++, tanggal);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Penjualan penjualan = new Penjualan();
                    penjualan.setId(rs.getInt("penjualan_id"));
                    penjualan.setJumlah(rs.getInt("jumlah"));
                    penjualan.setTotalHarga(rs.getDouble("total_harga"));
                    penjualan.setTanggal(rs.getString("tanggal"));
                    penjualan.setPelangganID(rs.getInt("pelanggan_id"));
                    filteredPenjualan.add(penjualan);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredPenjualan;
    }

    // Add new Penjualan to the database
    public static void addPenjualan(Penjualan penjualan) {
        String query = "INSERT INTO penjualan (jumlah, total_harga, tanggal, pelanggan_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, penjualan.getJumlah());
            stmt.setDouble(2, penjualan.getTotalHarga());
            stmt.setString(3, penjualan.getTanggal());
            stmt.setInt(4, penjualan.getPelangganID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update existing Penjualan in the database
    public static void updatePenjualan(Penjualan penjualan) {
        String query = "UPDATE penjualan SET jumlah = ?, total_harga = ?, tanggal = ?, pelanggan_id = ? WHERE penjualan_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, penjualan.getJumlah());
            stmt.setDouble(2, penjualan.getTotalHarga());
            stmt.setString(3, penjualan.getTanggal());
            stmt.setInt(4, penjualan.getPelangganID());
            stmt.setInt(5, penjualan.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a Penjualan from the database
    public static void deletePenjualan(int penjualanId) {
        String query = "DELETE FROM penjualan WHERE penjualan_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, penjualanId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}