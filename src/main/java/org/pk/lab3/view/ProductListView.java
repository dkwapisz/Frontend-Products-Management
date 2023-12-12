package org.pk.lab3.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.pk.lab3.utils.ViewPathFinals.MAIN_VIEW_PATH;
import static org.pk.lab3.utils.ViewPathFinals.PRODUCT_CREATE_VIEW_PATH;

public class ProductListView {

    public AnchorPane productListSceneView;

    public TableView<Product> productListTableView;
    public TableColumn<Product, String> idColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, Integer> quantityColumn;
    public TableColumn<Product, Float> priceColumn;
    public TableColumn<Product, Boolean> availabilityColumn;

    public Button backToMenuButton;
    public Button addProductButton;

    @FXML
    public void initialize() {
        List<Product> productList = createSampleProducts();
        productListTableView.getItems().addAll(productList);
    }

    private List<Product> createSampleProducts() {
        List<Product> productList = new ArrayList<>();

        productList.add(new Product("1", "Laptop",  10, 1200.0f, true));
        productList.add(new Product("2", "Smartphone", 50, 800.0f, true));
        productList.add(new Product("3", "Headphones",  30, 150.0f, true));
        productList.add(new Product("4", "Camera",  5, 2000.0f, true));
        productList.add(new Product("5", "Smartwatch",  20, 180.0f, true));

        return productList;
    }

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void addProductButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PRODUCT_CREATE_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
