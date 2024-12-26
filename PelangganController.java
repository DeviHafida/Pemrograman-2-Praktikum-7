package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.Pelanggan;

public class PelangganController {

    @FXML
    private TableView<Pelanggan> tablePelanggan;
    @FXML
    private TableColumn<Pelanggan, Integer> colPelangganId;
    @FXML
    private TableColumn<Pelanggan, String> colPelangganNama;
    @FXML
    private TableColumn<Pelanggan, String> colPelangganEmail;
    @FXML
    private TableColumn<Pelanggan, String> colPelangganTelepon;
    @FXML
    private TextField tfNama;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelepon;

    private ObservableList<Pelanggan> pelangganList = FXCollections.observableArrayList();

    public void initialize() {
        // Menghubungkan kolom dengan properti model
        colPelangganId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colPelangganNama.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        colPelangganEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colPelangganTelepon.setCellValueFactory(cellData -> cellData.getValue().teleponProperty());

        // Dummy data (dapat diganti dengan data dari database)
        pelangganList.addAll(
            new Pelanggan(1, "John Doe", "john@example.com", "08123456789"),
            new Pelanggan(2, "Jane Smith", "jane@example.com", "08198765432")
        );

        tablePelanggan.setItems(pelangganList);
    }

    @FXML
    public void handleAddPelanggan(ActionEvent event) {
        // Validasi input
        if (tfNama.getText().isEmpty() || tfEmail.getText().isEmpty() || tfTelepon.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Add Pelanggan", "All fields are required!");
            return;
        }

        // Tambahkan pelanggan baru
        int newId = pelangganList.size() + 1; // ID auto-increment sederhana
        Pelanggan newPelanggan = new Pelanggan(newId, tfNama.getText(), tfEmail.getText(), tfTelepon.getText());
        pelangganList.add(newPelanggan);

        // Reset input fields
        tfNama.clear();
        tfEmail.clear();
        tfTelepon.clear();

        showAlert(AlertType.INFORMATION, "Add Pelanggan", "Pelanggan added successfully!");
    }

    @FXML
    public void handleEditPelanggan(ActionEvent event) {
        Pelanggan selectedPelanggan = tablePelanggan.getSelectionModel().getSelectedItem();
        if (selectedPelanggan != null) {
            // Validasi input
            if (tfNama.getText().isEmpty() || tfEmail.getText().isEmpty() || tfTelepon.getText().isEmpty()) {
                showAlert(AlertType.WARNING, "Edit Pelanggan", "All fields are required!");
                return;
            }

            // Update data pelanggan
            selectedPelanggan.setNama(tfNama.getText());
            selectedPelanggan.setEmail(tfEmail.getText());
            selectedPelanggan.setTelepon(tfTelepon.getText());

            tablePelanggan.refresh(); // Refresh tabel untuk menampilkan perubahan

            showAlert(AlertType.INFORMATION, "Edit Pelanggan", "Pelanggan updated successfully!");
        } else {
            showAlert(AlertType.WARNING, "Edit Pelanggan", "No pelanggan selected!");
        }
    }

    @FXML
    public void handleDeletePelanggan(ActionEvent event) {
        Pelanggan selectedPelanggan = tablePelanggan.getSelectionModel().getSelectedItem();
        if (selectedPelanggan != null) {
            pelangganList.remove(selectedPelanggan); // Hapus pelanggan dari daftar

            showAlert(AlertType.INFORMATION, "Delete Pelanggan", "Pelanggan deleted successfully!");
        } else {
            showAlert(AlertType.WARNING, "Delete Pelanggan", "No pelanggan selected!");
        }
    }

    // Utility method untuk menampilkan alert
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}