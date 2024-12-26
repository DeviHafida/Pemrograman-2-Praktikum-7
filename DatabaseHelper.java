package controller;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Buku;
import model.Pelanggan;
import model.Penjualan;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/lentera pustaka"; // Adjust DB name
    private static final String USER = "root"; // Adjust DB username
    private static final String PASSWORD = ""; // Adjust DB password

    // Method to get a connection to the database
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            return null;
        }
    }

    // Load Buku data from the database
    public ObservableList<Buku> loadBukuData() {
        ObservableList<Buku> bukuList = FXCollections.observableArrayList();
        String query = "SELECT * FROM buku";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int bukuID = resultSet.getInt("buku_id");
                String judul = resultSet.getString("judul");
                String penulis = resultSet.getString("penulis");
                double harga = resultSet.getDouble("harga");
                int stok = resultSet.getInt("stok");
                bukuList.add(new Buku(bukuID, judul, penulis, harga, stok));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading buku data: " + e.getMessage());
        }

        return bukuList;
    }

    // Load Pelanggan data from the database
    public ObservableList<Pelanggan> loadPelangganData() {
        ObservableList<Pelanggan> pelangganList = FXCollections.observableArrayList();
        String query = "SELECT * FROM pelanggan";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int pelangganID = resultSet.getInt("pelanggan_id");
                String nama = resultSet.getString("nama");
                String email = resultSet.getString("email");
                String telepon = resultSet.getString("telepon");
                pelangganList.add(new Pelanggan(pelangganID, nama, email, telepon));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading pelanggan data: " + e.getMessage());
        }

        return pelangganList;
    }

    // Load Penjualan data from the database
    public ObservableList<Penjualan> loadPenjualanData() {
        ObservableList<Penjualan> penjualanList = FXCollections.observableArrayList();
        String query = "SELECT * FROM penjualan";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int penjualanID = resultSet.getInt("penjualan_id");
                int jumlah = resultSet.getInt("jumlah");
                double totalHarga = resultSet.getDouble("total_harga");
                String tanggal = resultSet.getString("tanggal");
                int pelangganID = resultSet.getInt("pelanggan_id");
                penjualanList.add(new Penjualan(penjualanID, jumlah, totalHarga, tanggal, pelangganID));
            }
        } catch (SQLException e) {
            System.err.println("Error while loading penjualan data: " + e.getMessage());
        }

        return penjualanList;
    }

    // Add a new Penjualan to the database
    public void addPenjualan(Penjualan penjualan) {
        String query = "INSERT INTO penjualan (jumlah, total_harga, tanggal, pelanggan_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, penjualan.getJumlah());
            statement.setDouble(2, penjualan.getTotalHarga());
            statement.setString(3, penjualan.getTanggal());
            statement.setInt(4, penjualan.getPelangganID());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding penjualan: " + e.getMessage());
        }
    }

    // Update an existing Penjualan in the database
    public void updatePenjualan(Penjualan penjualan) {
        String query = "UPDATE penjualan SET jumlah = ?, total_harga = ?, tanggal = ?, pelanggan_id = ? WHERE penjualan_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, penjualan.getJumlah());
            statement.setDouble(2, penjualan.getTotalHarga());
            statement.setString(3, penjualan.getTanggal());
            statement.setInt(4, penjualan.getPelangganID());
            statement.setInt(5, penjualan.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating penjualan: " + e.getMessage());
        }
    }

    // Delete a Penjualan from the database
    public void deletePenjualan(Penjualan penjualan) {
        String query = "DELETE FROM penjualan WHERE penjualan_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, penjualan.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deleting penjualan: " + e.getMessage());
        }
    }
    
    	Connection conn = DatabaseHelper.getConnection();{
    		if (conn == null) {
    			System.out.println("Koneksi database gagal!");
    		} else {
    			System.out.println("Koneksi database berhasil!");
    	}
    
    }

}