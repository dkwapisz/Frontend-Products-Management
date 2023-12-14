package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static org.pk.lab3.utils.ViewPathFinals.MAIN_VIEW_PATH;

public class ProductCreateViewModel {

    public AnchorPane productCreateSceneView;

    public TextField idTextField;
    public TextField nameTextField;
    public TextField descriptionTextField;
    public TextField priceTextField;
    public TextField weightTextField;
    public TextField availabilityTextField;

    public ComboBox<String> productCategoryComboBox;
    public Spinner<Integer> quantitySpinner;
    public Label promptLabel;

    public Button backToMenuButton;
    public Button createProductButton;

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) productCreateSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void createProductButtonOnClick() {
        // TODO Send data to ViewModel
        System.out.println("Add Product");
    }

    private void clearPromptLabel() {
        promptLabel.setText("");
    }
}
