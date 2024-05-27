package com.cruiseproject.Controllers;

import com.cruiseproject.DAO.TicketDAO;
import com.cruiseproject.Items.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

// Клас контроллер повертання квитків
public class DeleteTicketController {
    @FXML
    private TextField del_ticket_IDTicketTextField;

    private static Stage window;
    private int ticketID;
    private Ticket selectedTicket;

    // Метод подія на клік "Підтвердити"
    @FXML
    private void onDeleteTicketButtonClick() {
        try { // Якщо айді введено коректно,...
            String textID = del_ticket_IDTicketTextField.getText();
            ticketID = Integer.parseInt(textID);

            try { // ...то пробуємо знайти квиток в базі даних.
                selectedTicket = TicketDAO.findByID(ticketID);

                // Якщо квиток знайдено, то виводимо підтвердження повернення квитка
                if (selectedTicket != null && selectedTicket.getId() != 0){
                    String confirmationMessage = "Ви точно хочете відмовитись від квитка?";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Підтвердження");
                    alert.setHeaderText(null);

                    Optional<ButtonType> result = alert.showAndWait();

                    // Якщо підтвердження відбувається, то видаляємо квиток з бази даних
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        boolean flag = TicketDAO.removeTicketByID(ticketID);

                        if (flag){ // Повідомляємо користувача про успішне повернення
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Успішно");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Квиток успішно повернуто.");
                            successAlert.showAndWait();
                            window.close();
                        }
                        else { // Повідомляємо користувача про помилку, якщо не вдалося повернути квиток
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Помилка");
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Помилка у видаленні квитка");
                            errorAlert.showAndWait();
                        }
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Квиток за даним ID не знайдено");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Помилка в знаходженні квитка");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Некоректний ID квитка");
            alert.showAndWait();
        }
    }

    // Метод відображення модульного вікна видалення квитків
    public static void delTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(DeleteTicketController.class.getResource("/com/cruiseproject/windows/delete-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 480, 270);
            window = new Stage();
            window.getIcons().add(new Image(Objects.requireNonNull(DeleteTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(DeleteTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(DeleteTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Відмінити квиток");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
