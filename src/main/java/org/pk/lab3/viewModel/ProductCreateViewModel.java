package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.pk.lab3.model.Product;
import org.pk.lab3.model.ProductCategory;
import org.pk.lab3.service.cache.CachingService;
import org.pk.lab3.service.http.ProductService;
import org.pk.lab3.config.AppConfig;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.pk.lab3.model.ProductCategory.*;

public class ProductCreateViewModel {

    public AnchorPane productCreateSceneView;

    public TextField nameTextField;
    public TextField descriptionTextField;
    public TextField priceTextField;
    public TextField weightTextField;

    public ComboBox<ProductCategory> productCategoryComboBox;
    public Spinner<Integer> quantitySpinner;
    public Label promptLabel;

    public Button backToMenuButton;
    public Button createProductButton;

    @FXML
    public void initialize() {
        initializeCategoriesComboBox();
        initializeQuantitySpinner();
    }

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getMainViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) productCreateSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void createProductButtonOnClick() {
        if (validateProductData()) {
            Product product = ProductService.getInstance().createProduct(createProductFromFields());

            if (nonNull(product)) {
                clearAllFields();
                updateCache(product.getId(), product);
                promptLabel.setText("Product has been created");
            } else {
                promptLabel.setText("Server does not responding");
            }
        }
    }

    private Product createProductFromFields() {
        return Product.builder()
                .name(nameTextField.getText())
                .description(descriptionTextField.getText())
                .price(Float.parseFloat(priceTextField.getText()))
                .weight(Float.parseFloat(weightTextField.getText()))
                .productCategory(productCategoryComboBox.getSelectionModel().getSelectedItem())
                .quantity(quantitySpinner.getValue())
                .build();
    }

    private boolean validateProductData() {
        if (isNull(nameTextField) || nameTextField.getText().isEmpty() ||
                isNull(descriptionTextField) || descriptionTextField.getText().isEmpty() ||
                isNull(priceTextField) || priceTextField.getText().isEmpty() ||
                isNull(weightTextField) || weightTextField.getText().isEmpty() ||
                isNull(quantitySpinner.getValue()) || productCategoryComboBox.getSelectionModel().isEmpty()) {

            promptLabel.setText("Some of values are empty. Aborting creation...");
            return false;
        }

        try {
            Float.parseFloat(priceTextField.getText());
            Float.parseFloat(weightTextField.getText());
        } catch (NumberFormatException ignored) {
            promptLabel.setText("Price and weight has to be positive numeric value. Aborting creation...");
            return false;
        }

        if (nameTextField.getText().length() < 3) {
            promptLabel.setText("Product name has to be longer than 3 characters");
            return false;
        }

        if (descriptionTextField.getText().length() < 3) {
            promptLabel.setText("Product description has to be longer than 3 characters");
            return false;
        }

        return true;
    }

    private void clearAllFields() {
        nameTextField.clear();
        descriptionTextField.clear();
        priceTextField.clear();
        weightTextField.clear();

        productCategoryComboBox.getSelectionModel().clearSelection();
        quantitySpinner.getValueFactory().setValue(0);
    }

    private void initializeCategoriesComboBox() {
        productCategoryComboBox.getItems().addAll(ELECTRONICS, MOTORS, COLLECTIBLES, HOME, GARDEN, FASHION, TOYS,
                SPORT, INDUSTRIAL, JEWELRY);
    }

    private void initializeQuantitySpinner() {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));

        quantitySpinner.getValueFactory().setConverter(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch (NumberFormatException e) {
                    promptLabel.setText("Please input Integer value in quantity field.");
                    return quantitySpinner.getValue();
                }
            }
        });
        TextFormatter<Integer> textFormatter = new TextFormatter<>(quantitySpinner.getValueFactory().getConverter(),
                quantitySpinner.getValue());
        quantitySpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void updateCache(String productId, Product product) {
        CachingService.getInstance().addProductDetailsToCache(productId, product);
        CachingService.getInstance()
                .addProductSummaryListToCache("productList", ProductService.getInstance().getAllProducts());
    }
}
