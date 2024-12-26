package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private Tab tabBuku;

    @FXML
    private Tab tabPelanggan;

    @FXML
    private Tab tabPenjualan;

    @FXML
    public void initialize() {
        // Load Buku.fxml when Buku tab is selected
        tabBuku.setOnSelectionChanged(event -> {
            if (tabBuku.isSelected() && tabBuku.getContent() == null) {
                loadView("/view/Buku.fxml", tabBuku);
            }
        });

        // Load Pelanggan.fxml when Pelanggan tab is selected
        tabPelanggan.setOnSelectionChanged(event -> {
            if (tabPelanggan.isSelected() && tabPelanggan.getContent() == null) {
                loadView("/view/Pelanggan.fxml", tabPelanggan);
            }
        });

        // Load Penjualan.fxml when Penjualan tab is selected
        tabPenjualan.setOnSelectionChanged(event -> {
            if (tabPenjualan.isSelected() && tabPenjualan.getContent() == null) {
                loadView("/view/Penjualan.fxml", tabPenjualan);
            }
        });
    }

    // Utility method to load the view
    private void loadView(String resourcePath, Tab targetTab) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resourcePath));
            AnchorPane view = loader.load();
            targetTab.setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}