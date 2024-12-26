package controller;

import model.Penjualan;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class PenjualanController {

    @FXML
    private TableView<Penjualan> penjualanTableView;
    @FXML
    private TableColumn<Penjualan, Integer> idColumn;
    @FXML
    private TableColumn<Penjualan, Integer> jumlahColumn;
    @FXML
    private TableColumn<Penjualan, Double> totalHargaColumn;
    @FXML
    private TableColumn<Penjualan, String> tanggalColumn;
    @FXML
    private TableColumn<Penjualan, Integer> pelangganIDColumn;

    @FXML
    private TextField jumlahField;
    @FXML
    private TextField totalHargaField;
    @FXML
    private TextField tanggalField;
    @FXML
    private TextField pelangganIDField;

    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    
    private ObservableList<Penjualan> penjualanList;

    public void initialize() {
        // Set up table columns
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        jumlahColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahProperty().asObject());
        totalHargaColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty().asObject());
        tanggalColumn.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        pelangganIDColumn.setCellValueFactory(cellData -> cellData.getValue().pelangganIDProperty().asObject());

        // Load data into table
        loadPenjualanData();

        // Set up table row click listener for editing
        penjualanTableView.setOnMouseClicked(this::handleTableRowClick);
    }

    // Load all Penjualan from the database and display in table
    private void loadPenjualanData() {
        penjualanList = Penjualan.loadAllPenjualan();
        penjualanTableView.setItems(penjualanList);
    }

    // Handle table row click for editing fields
    private void handleTableRowClick(MouseEvent event) {
        Penjualan selectedPenjualan = penjualanTableView.getSelectionModel().getSelectedItem();
        if (selectedPenjualan != null) {
            jumlahField.setText(String.valueOf(selectedPenjualan.getJumlah()));
            totalHargaField.setText(String.valueOf(selectedPenjualan.getTotalHarga()));
            tanggalField.setText(selectedPenjualan.getTanggal());
            pelangganIDField.setText(String.valueOf(selectedPenjualan.getPelangganID()));
        }
    }

    // Add a new Penjualan
    @FXML
    private void handleAddPenjualan() {
        try {
            int jumlah = Integer.parseInt(jumlahField.getText());
            double totalHarga = Double.parseDouble(totalHargaField.getText());
            String tanggal = tanggalField.getText();
            int pelangganID = Integer.parseInt(pelangganIDField.getText());

            Penjualan penjualan = new Penjualan(0, jumlah, totalHarga, tanggal, pelangganID);
            Penjualan.addPenjualan(penjualan);

            // Reload table data
            loadPenjualanData();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
        }
    }

    // Update selected Penjualan
    @FXML
    private void handleUpdatePenjualan() {
        Penjualan selectedPenjualan = penjualanTableView.getSelectionModel().getSelectedItem();
        if (selectedPenjualan != null) {
            try {
                int jumlah = Integer.parseInt(jumlahField.getText());
                double totalHarga = Double.parseDouble(totalHargaField.getText());
                String tanggal = tanggalField.getText();
                int pelangganID = Integer.parseInt(pelangganIDField.getText());

                selectedPenjualan.setJumlah(jumlah);
                selectedPenjualan.setTotalHarga(totalHarga);
                selectedPenjualan.setTanggal(tanggal);
                selectedPenjualan.setPelangganID(pelangganID);

                Penjualan.updatePenjualan(selectedPenjualan);

                // Reload table data
                loadPenjualanData();
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers.");
            }
        }
    }

    // Delete selected Penjualan
    @FXML
    private void handleDeletePenjualan() {
        Penjualan selectedPenjualan = penjualanTableView.getSelectionModel().getSelectedItem();
        if (selectedPenjualan != null) {
            Penjualan.deletePenjualan(selectedPenjualan.getId());
            loadPenjualanData();
        }
    }

    // Show alert message
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}