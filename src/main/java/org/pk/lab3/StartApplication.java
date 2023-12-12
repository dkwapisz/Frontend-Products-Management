package org.pk.lab3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.pk.lab3.utils.ViewPathFinals.MAIN_VIEW_PATH;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAIN_VIEW_PATH));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Product Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}