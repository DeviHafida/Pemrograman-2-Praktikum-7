package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Buku;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class BukuController {

    @FXML
    private TableView<Buku> tableViewBuku;  // Pastikan fx:id di FXML sesuai
    @FXML
    private TableColumn<Buku, Integer> colBukuID;
    @FXML
    private TableColumn<Buku, String> colBukuTitle;
    @FXML
    private TableColumn<Buku, String> colPenulisAuthor;
    @FXML
    private TableColumn<Buku, Double> colBukuPrice;
    @FXML
    private TableColumn<Buku, Integer> colBukuStok;
    
    @FXML
    private TextField tfTitle;
    
    @FXML
    private TextField tfAuthor;
    
    @FXML
    private TextField tfPrice;
    
    @FXML
    private TextField tfStok;

    @FXML
    private Button btnAddBuku;
    
    @FXML
    private Button btnDeleteBuku;
    
    @FXML
    private Button btnEditBuku;

    private ObservableList<Buku> bukuList = FXCollections.observableArrayList();

    // Ini akan dipanggil otomatis setelah FXML dimuat
    public void initialize() {
    	DatabaseHelper dbHelper = new DatabaseHelper();
        bukuList = dbHelper.loadBukuData();
        // Inisialisasi kolom tabel
        colBukuID.setCellValueFactory(cellData -> cellData.getValue().bukuidProperty().asObject());
        colBukuTitle.setCellValueFactory(cellData -> cellData.getValue().judulProperty());
        colPenulisAuthor.setCellValueFactory(cellData -> cellData.getValue().penulisProperty());
        colBukuPrice.setCellValueFactory(cellData -> cellData.getValue().hargaProperty().asObject());
        colBukuStok.setCellValueFactory(cellData -> cellData.getValue().stokProperty().asObject());

        // Memuat data buku dari database
        loadBukuData();
    }

    private void loadBukuData() {
        bukuList.clear(); // Bersihkan list sebelum menambah data baru
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lentera pustaka", "root", "")) {
            String query = "SELECT * FROM buku";  
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Buku buku = new Buku(
                            rs.getInt("buku_id"),  
                            rs.getString("judul"),
                            rs.getString("penulis"),
                            rs.getDouble("harga"),
                            rs.getInt("stok")
                    );
                    bukuList.add(buku);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableViewBuku.setItems(bukuList);
    }


    @FXML
    public void handleAddBuku(ActionEvent event) {
        // Menambahkan buku baru
        Buku newBuku = new Buku(3, tfTitle.getText(), tfAuthor.getText(), Double.parseDouble(tfPrice.getText()), Integer.parseInt(tfStok.getText()));
        bukuList.add(newBuku);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Add Buku");
        alert.setHeaderText("Buku Added Successfully");
        alert.showAndWait();
    }

    @FXML
    public void handleEditBuku(ActionEvent event) {
        if (tableViewBuku.getSelectionModel().getSelectedItem() != null) {
            Buku selectedBuku = tableViewBuku.getSelectionModel().getSelectedItem();
            selectedBuku.setJudul(tfTitle.getText());
            selectedBuku.setPenulis(tfAuthor.getText());
            selectedBuku.setHarga(Double.parseDouble(tfPrice.getText()));
            selectedBuku.setStok(Integer.parseInt(tfStok.getText()));

            tableViewBuku.refresh();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Edit Buku");
            alert.setHeaderText("Buku Updated Successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Edit Buku");
            alert.setHeaderText("No Buku Selected");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleDeleteBuku(ActionEvent event) {
        Buku selectedBuku = tableViewBuku.getSelectionModel().getSelectedItem();
        if (selectedBuku != null) {
            bukuList.remove(selectedBuku);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Delete Buku");
            alert.setHeaderText("Buku Deleted Successfully");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Delete Buku");
            alert.setHeaderText("No Buku Selected");
            alert.showAndWait();
        }
    }
    
    public void refreshData() {
        DatabaseHelper dbHelper = new DatabaseHelper();
        bukuList = dbHelper.loadBukuData(); 
        tableViewBuku.setItems(bukuList);
    }

}