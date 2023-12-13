package org.pk.lab3.view;

import javafx.collections.ObservableList;
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
import org.pk.lab3.model.ProductSummary;

import java.io.IOException;

import static java.util.Objects.isNull;
import static javafx.collections.FXCollections.observableArrayList;
import static org.pk.lab3.utils.ViewPathFinals.*;

public class ProductListView {

    public AnchorPane productListSceneView;

    public TableView<ProductSummary> productListTableView;
    public TableColumn<ProductSummary, String> idColumn;
    public TableColumn<ProductSummary, String> nameColumn;
    public TableColumn<ProductSummary, Integer> quantityColumn;
    public TableColumn<ProductSummary, Float> priceColumn;
    public TableColumn<ProductSummary, Boolean> availabilityColumn;

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

        ObservableList<ProductSummary> productList = createSampleProducts();
        productListTableView.getItems().addAll(productList);

        initializeDoubleClickListener(productListTableView);
    }

    // TODO To Remove
    private ObservableList<ProductSummary> createSampleProducts() {
        ObservableList<ProductSummary> productList = observableArrayList();

        productList.add(new ProductSummary("1", "Laptop",  10, 1200.0f, true));
        productList.add(new ProductSummary("2", "Smartphone", 50, 800.0f, true));
        productList.add(new ProductSummary("3", "Headphones",  30, 150.0f, true));
        productList.add(new ProductSummary("4", "Camera",  5, 2000.0f, true));
        productList.add(new ProductSummary("5", "Smartwatch",  20, 180.0f, true));

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

    private void initializeDoubleClickListener(TableView<ProductSummary> tableView) {
        tableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<ProductSummary> row = new javafx.scene.control.TableRow<>();
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

    private void handleRowDoubleClick(TableRow<ProductSummary> row) throws IOException {
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
