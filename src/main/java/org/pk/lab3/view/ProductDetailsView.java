package org.pk.lab3.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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

    public ComboBox<String> productCategoryComboBox;
    public Spinner<Integer> quantitySpinner;
    public Label promptLabel;
    public TreeView historyTreeView; //TODO History (?)

    public Button backToMenuButton;
    public Button deleteButton;
    public Button editButton;

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) productDetailsSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void deleteButtonOnClick() {
        System.out.println("Delete Product");
    }

    @FXML
    public void editButtonOnClick() {
        System.out.println("Edit Product");
    }


}
