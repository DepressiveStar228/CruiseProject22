package com.cruiseproject.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class InfoController {
    @FXML
    private Label infoNameLabel;

    @FXML
    private Label infoTextLabel;

    private static Stage window;
    private static String info_text;
    private static boolean mod;


    public void initialize() {
        if (mod) {
            infoNameLabel.setText("Про нас");
            info_text = readTextFile("about_us_text.txt");
        }
        else {
            infoNameLabel.setText("Довідка");
            info_text = readTextFile("information_text.txt");
        }

        infoTextLabel.setText(info_text);
    }

    public static void info(boolean modType) {
        FXMLLoader fxmlLoader = new FXMLLoader(InfoController.class.getResource("/com/cruiseproject/info.fxml"));
        mod = modType;

        if (modType){
            try {
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                window = new Stage();
                window.setResizable(false);
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Про нас");
                window.setScene(scene);
                window.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                window = new Stage();
                window.setResizable(false);
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Довідка");
                window.setScene(scene);
                window.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String readTextFile(String filename){
        StringBuilder content = new StringBuilder();
        String path = "D:\\Projects\\lab1\\JavaProject\\src\\main\\resources\\com\\cruiseproject\\";
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path + filename), "UTF-8"))) {
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Помилка считання даних з " + filename);
            alert.showAndWait();
            return null;
        }
    }
}
