package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.model.ProductSummary;
import org.pk.lab3.service.cache.CachingService;
import org.pk.lab3.service.http.ProductService;
import org.pk.lab3.utils.AppConfig;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;

public class ProductListViewModel {

    public AnchorPane productListSceneView;

    public TableView<ProductSummary> productListTableView;
    public TableColumn<ProductSummary, String> idColumn;
    public TableColumn<ProductSummary, String> nameColumn;
    public TableColumn<ProductSummary, Integer> quantityColumn;
    public TableColumn<ProductSummary, Float> priceColumn;
    public TableColumn<ProductSummary, Boolean> availabilityColumn;

    public Button backToMenuButton;
    public Button addProductButton;
    public Button refreshButton;

    public Label promptLabel;

    private long lastClickTime = 0;

    @FXML
    public void initialize() {
        clearPromptLabel();
        initializeDoubleClickListener(productListTableView);

        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        refreshList(false);
    }

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getMainViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void addProductButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getProductCreateViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void refreshButtonOnClick() {
        refreshList(true);
    }

    private void refreshList(boolean forceRefresh) {
        List<ProductSummary> productSummaries;

        if (forceRefresh) {
            productSummaries = ProductService.getInstance().getAllProducts();
        } else {
            productSummaries = getProductListFromCacheOrService();
        }

        if (isNull(productSummaries)) {
            promptLabel.setText("Server does not responding.");
        } else {
            clearPromptLabel();
            productListTableView.getItems().clear();
            productListTableView.getItems().addAll(productSummaries);
        }
    }

    private void initializeDoubleClickListener(TableView<ProductSummary> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<ProductSummary> row = new TableRow<>();
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getProductDetailsViewPath()));
        Parent root = loader.load();
        ProductDetailsViewModel productDetailsViewModel = loader.getController();
        productDetailsViewModel.initializeProductData(row.getItem().getId());
        Stage stage = (Stage) productListSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void clearPromptLabel() {
        promptLabel.setText("");
    }

    private List<ProductSummary> getProductListFromCacheOrService() {
        List<ProductSummary> productListFromCache = CachingService.getInstance().getProductSummaryListCache("productList");

        if (isNull(productListFromCache)) {
            List<ProductSummary> productListFromService = ProductService.getInstance().getAllProducts();
            CachingService.getInstance().addProductSummaryListToCache("productList", productListFromService);
            return productListFromService;
        } else {
            return productListFromCache;
        }
    }
}
