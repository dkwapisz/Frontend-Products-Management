package org.pk.lab3.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.model.ProductCategory;

import java.io.IOException;

import static org.pk.lab3.model.ProductCategory.*;
import static org.pk.lab3.utils.ViewPathFinals.MAIN_VIEW_PATH;

public class ProductDetailsView {

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
    public TreeView historyTreeView; // TODO History (?)

    public Button backToMenuButton;
    public Button deleteButton;
    public Button editButton;
    public Button editSaveButton;

    @FXML
    public void initialize() {
        initializeCategoriesComboBox();
        initializeQuantitySpinner();
        disableFieldsEditing();
    }

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) productDetailsSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void deleteButtonOnClick() {
        clearProductDataFromView();
        disableFieldsEditing();
        disableSaveButton();
        enableEditButton();

        // TODO Send Delete to ViewModel
        System.out.println("Delete Product");
    }

    @FXML
    public void editButtonOnClick() {
        enableFieldsEditing();
        enableSaveButton();
        disableEditButton();

        System.out.println("Edit Product");
    }

    @FXML
    public void saveButtonOnClick() {
        disableFieldsEditing();
        disableSaveButton();
        enableEditButton();

        // TODO Send Commit to ViewModel
        System.out.println("Edit Product");
    }

    public void initializeProductData(String id) {
        // TODO Get Product Details from ViewModel

        String testString = "TEST";

        idTextField.setText(id);
        nameTextField.setText(testString);
        descriptionTextField.setText(testString);
        priceTextField.setText(testString);
        weightTextField.setText(testString);
        availabilityTextField.setText(testString);
        creationDateTextField.setText(testString);
        lastUpdateTextField.setText(testString);

        productCategoryComboBox.setValue(ELECTRONICS);
        quantitySpinner.getValueFactory().setValue(10);
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
        idTextField.setDisable(false);
        nameTextField.setDisable(false);
        descriptionTextField.setDisable(false);
        priceTextField.setDisable(false);
        weightTextField.setDisable(false);
        availabilityTextField.setDisable(false);
        creationDateTextField.setDisable(false);
        lastUpdateTextField.setDisable(false);
        productCategoryComboBox.setDisable(false);
        quantitySpinner.setDisable(false);
    }

    private void disableFieldsEditing() {
        idTextField.setDisable(true);
        nameTextField.setDisable(true);
        descriptionTextField.setDisable(true);
        priceTextField.setDisable(true);
        weightTextField.setDisable(true);
        availabilityTextField.setDisable(true);
        creationDateTextField.setDisable(true);
        lastUpdateTextField.setDisable(true);
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
}
