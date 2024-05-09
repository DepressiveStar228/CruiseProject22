package com.cruiseproject.Controllers;

import com.cruiseproject.DAO.TicketDAO;
import com.cruiseproject.Items.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteTicketController {
    @FXML
    private TextField del_ticket_IDTicketTextField;

    private static Stage window;
    private int ticketID;
    private Ticket selectedTicket;

    @FXML
    private void onDeleteTicketButtonClick() {
        try {
            String textID = del_ticket_IDTicketTextField.getText();
            ticketID = Integer.parseInt(textID);

            try {
                selectedTicket = TicketDAO.findByID(ticketID);

                if (selectedTicket != null){
                    String confirmationMessage = "Ви точно хочете відмовитись від квитка?";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Підтвердження");
                    alert.setHeaderText(null);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.YES) {
                        boolean flag = TicketDAO.removeTicketByID(ticketID);

                        if (flag){
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Успішно");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Квиток успішно повернуто.");
                            successAlert.showAndWait();
                            window.close();
                        }
                        else {
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

    public static void delTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(DeleteTicketController.class.getResource("/com/cruiseproject/delete-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 480, 270);
            window = new Stage();
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
