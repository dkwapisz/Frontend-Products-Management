package org.pk.lab3.viewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pk.lab3.utils.AppConfig;

import java.io.IOException;

public class MainViewModel {

    public AnchorPane mainSceneView;

    public Button addProductButton;
    public Button showProductListButton;

    @FXML
    public void addProductButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getProductCreateViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) mainSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void showProductListButtonOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(AppConfig.getInstance().getProductListViewPath()));
        Parent root = loader.load();
        Stage stage = (Stage) mainSceneView.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
