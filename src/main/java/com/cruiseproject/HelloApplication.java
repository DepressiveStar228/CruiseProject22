package com.cruiseproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/cruiseproject/windows/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
        stage.setResizable(false);
        stage.setTitle("Cruise Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

//onAction="#onPrintClick"