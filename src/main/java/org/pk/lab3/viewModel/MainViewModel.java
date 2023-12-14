package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static org.pk.lab3.utils.ViewPathFinals.PRODUCT_CREATE_VIEW_PATH;
import static org.pk.lab3.utils.ViewPathFinals.PRODUCT_LIST_VIEW_PATH;

public class MainViewModel {

    public AnchorPane mainSceneView;

    public Button addProductButton;
    public Button showProductListButton;

    @FXML
    public void backToMenuButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PRODUCT_CREATE_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) mainSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void showProductListButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PRODUCT_LIST_VIEW_PATH));
        Parent root = loader.load();
        Stage stage = (Stage) mainSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
