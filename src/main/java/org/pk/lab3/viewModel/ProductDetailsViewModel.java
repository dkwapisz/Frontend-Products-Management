package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.model.Product;
import org.pk.lab3.model.ProductCategory;
import org.pk.lab3.service.model.ProductService;
import org.pk.lab3.utils.AppConfig;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.pk.lab3.model.ProductCategory.*;

public class ProductDetailsViewModel {

    public AnchorPane productDetailsSceneView;

    public TextField idTextField;
    public TextField nameTextField;
    public TextField descriptionTextField;
    public TextField priceTextField;
    public TextField weightTextField;
    public TextField availabilityTextField;
    public TextField creationDateTextField;
    public TextField lastUpdateTextField;

    public ComboBox<ProductCategory> productCategoryComboBox;
    public Spinner<Integer> quantitySpinner;

    public Label promptLabel;

    public Button backToMenuButton;
    public Button deleteButton;
    public Button editButton;
    public Button editSaveButton;

    @FXML
    public void initialize() {
        clearPromptLabel();
        initializeCategoriesComboBox();
        initializeQuantitySpinner();
        disableFieldsEditing();
    }

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getMainViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) productDetailsSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void deleteButtonOnClick() {
        boolean deleted = ProductService.getInstance().deleteProduct(idTextField.getText());

        if (deleted) {
            promptLabel.setText("Product has been removed");
            clearProductDataFromView();
            disableFieldsEditing();
            disableSaveButton();
            enableEditButton();
        }

        promptLabel.setText("Server does not responding");
    }

    @FXML
    public void editButtonOnClick() {
        clearPromptLabel();
        enableFieldsEditing();
        enableSaveButton();
        disableEditButton();
    }

    @FXML
    public void saveButtonOnClick() {
        disableFieldsEditing();
        disableSaveButton();
        enableEditButton();

        Product editedProduct = createProductObjectFromFieldsData();

        if (nonNull(editedProduct)) {
            boolean edited = ProductService.getInstance().updateProduct(idTextField.getText(), editedProduct);

            if (edited) {
                promptLabel.setText("Product has been updated");
                initializeProductData(idTextField.getText());
            }
        }
    }

    public void initializeProductData(String id) {
        Product product = ProductService.getInstance().getProductDetails(id);

        if (isNull(product)) {
            promptLabel.setText("Server does not responding");
            return;
        } else if (isNull(product.getId())) {
            promptLabel.setText("Cannot found product");
            return;
        }

        idTextField.setText(id);
        nameTextField.setText(product.getName());
        descriptionTextField.setText(product.getDescription());
        priceTextField.setText(String.valueOf(product.getPrice()));
        weightTextField.setText(String.valueOf(product.getWeight()));
        availabilityTextField.setText(String.valueOf(product.getAvailable()));
        creationDateTextField.setText(String.valueOf(product.getDateAdded()));
        lastUpdateTextField.setText(String.valueOf(product.getDateLastUpdate()));

        productCategoryComboBox.setValue(product.getProductCategory());
        quantitySpinner.getValueFactory().setValue(product.getQuantity());
    }

    public Product createProductObjectFromFieldsData() {
        Float price = validateFloatValue(priceTextField);
        Float weight = validateFloatValue(weightTextField);

        if (Objects.equals(price, -1f) || Objects.equals(weight, -1f)) {
            return null;
        }

        return Product.builder()
                .name(nameTextField.getText())
                .description(descriptionTextField.getText())
                .quantity(quantitySpinner.getValue())
                .price(price)
                .weight(weight)
                .productCategory(productCategoryComboBox.getSelectionModel().getSelectedItem())
                .build();
    }

    private void clearProductDataFromView() {
        idTextField.clear();
        nameTextField.clear();
        descriptionTextField.clear();
        priceTextField.clear();
        weightTextField.clear();
        availabilityTextField.clear();
        creationDateTextField.clear();
        lastUpdateTextField.clear();

        productCategoryComboBox.getItems().clear();
        quantitySpinner.getValueFactory().setValue(0);
    }

    private void initializeCategoriesComboBox() {
        productCategoryComboBox.getItems().addAll(ELECTRONICS, MOTORS, COLLECTIBLES, HOME, GARDEN, FASHION, TOYS,
                SPORT, INDUSTRIAL, JEWELRY);
    }

    private void initializeQuantitySpinner() {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
    }

    private void enableFieldsEditing() {
        nameTextField.setDisable(false);
        descriptionTextField.setDisable(false);
        priceTextField.setDisable(false);
        weightTextField.setDisable(false);
        productCategoryComboBox.setDisable(false);
        quantitySpinner.setDisable(false);
    }

    private void disableFieldsEditing() {
        nameTextField.setDisable(true);
        descriptionTextField.setDisable(true);
        priceTextField.setDisable(true);
        weightTextField.setDisable(true);
        productCategoryComboBox.setDisable(true);
        quantitySpinner.setDisable(true);
    }

    private void enableSaveButton() {
        editSaveButton.setDisable(false);
    }

    private void disableSaveButton() {
        editSaveButton.setDisable(true);
    }

    private void enableEditButton() {
        editButton.setDisable(false);
    }

    private void disableEditButton() {
        editButton.setDisable(true);
    }

    private void clearPromptLabel() {
        promptLabel.setText("");
    }

    private Float validateFloatValue(TextField textField) {
        if (isNull(textField.getText()) || textField.getText().isEmpty()) {
            return null;
        }

        try {
            float number = Float.parseFloat(textField.getText());

            if (number <= 0) {
                promptLabel.setText("Price and weight has to be positive numeric value. Aborting edit...");
                return -1f;
            }

            return number;
        } catch (NumberFormatException e) {
            promptLabel.setText("Price and weight has to be positive numeric value. Aborting edit...");
            return -1f;
        }
    }
}
