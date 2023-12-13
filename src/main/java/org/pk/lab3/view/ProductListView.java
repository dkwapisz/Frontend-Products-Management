package org.pk.lab3.view;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.pk.lab3.utils.ViewPathFinals.*;

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

    private long lastClickTime = 0;

    @FXML
    public void initialize() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        // TODO Get data from ViewModel

        ObservableList<Product> productList = createSampleProducts();
        productListTableView.getItems().addAll(productList);

        initializeDoubleClickListener(productListTableView);
    }

    // TODO To Remove
    private ObservableList<Product> createSampleProducts() {
        ObservableList<Product> productList = observableArrayList();

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

    private void initializeDoubleClickListener(TableView<Product> tableView) {
        tableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Product> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime < 300) {
                    try {
                        handleRowDoubleClick(row);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                lastClickTime = currentTime;
            });
            return row;
        });
    }

    private void handleRowDoubleClick(TableRow<Product> row) throws IOException {
        if (row.isEmpty() || isNull(row.getItem()) || isNull(row.getItem().getId())) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(PRODUCT_DETAILS_VIEW_PATH));
        Parent root = loader.load();
        ProductDetailsView productDetailsView = loader.getController();
        productDetailsView.initializeProductData(row.getItem().getId());
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
