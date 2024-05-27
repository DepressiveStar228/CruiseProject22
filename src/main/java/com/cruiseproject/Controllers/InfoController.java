package com.cruiseproject.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// Клас контроллер виводу інформації
public class InfoController {
    @FXML
    private Label infoNameLabel;

    @FXML
    private Label infoTextLabel;

    private static Stage window;
    private static String info_text;
    private static boolean mod;


    // Метод ініціалізації назв модульних вікон та файлів з даними
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

    // Метод відкриття модульних вікон
    public static void info(boolean modType) {
        FXMLLoader fxmlLoader = new FXMLLoader(InfoController.class.getResource("/com/cruiseproject/windows/info.fxml"));
        mod = modType;

        if (modType){  // Відкриття модульного вікна "Про нас"
            try {
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                window = new Stage();
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
                window.setResizable(false);
                window.initModality(Modality.APPLICATION_MODAL);
                window.setTitle("Про нас");
                window.setScene(scene);
                window.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {  // Відкриття модульного вікна "Довідка"
            try {
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                window = new Stage();
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
                window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
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

    // Метод читання тексту з файлу
    private static String readTextFile(String filename){
        StringBuilder content = new StringBuilder();
        String path = "com/cruiseproject/text_files/" + filename; // Шлях до файлів з текстом
        String line;

        // Пробуємо читати дані
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getFile(path), StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (IOException e) {  // Повідомлення про помилку, якщо не вдалося
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Помилка считання даних з " + filename);
            alert.showAndWait();
            return null;
        }
    }

    // Метод пошуку файлу за шляхом
    private static InputStream getFile(String part){
        InputStream resource = InfoController.class.getClassLoader().getResourceAsStream(part);
        if (resource == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Файл з даними не знайдено");
            alert.showAndWait();
        }
        return resource;
    }
}
