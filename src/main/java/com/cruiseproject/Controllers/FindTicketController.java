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

// Клас контроллер пошуку квитків
public class FindTicketController {
    @FXML
    private Label find_ticket_Label1;

    @FXML
    private Label find_ticket_Label2;

    @FXML
    private Label find_ticket_Label3;

    @FXML
    private Label find_ticket_Label4;

    @FXML
    private Label find_ticket_Label5;

    @FXML
    private Label find_ticket_Label7;

    @FXML
    private Label find_ticket_Label8;

    @FXML
    private Label find_ticket_idCruise_Label;

    @FXML
    private Label find_ticket_nameCruise_Label;

    @FXML
    private Label find_ticket_namePeople_Label;

    @FXML
    private Label find_ticket_company_Label;

    @FXML
    private Label find_ticket_liner_Label;

    @FXML
    private Label find_ticket_price_Label;

    @FXML
    private TextField find_ticket_IDTicketTextField;

    @FXML
    private Button find_ticket_getIDTicketButton;

    private static Stage window;
    private int ticketID;
    private Ticket selectedTicket;


    // Метод ініціалізації модульного вікна для пошуку
    public void initialize() {
        changeVisibleItem(false);
    }

    // Метод подія на клік "Підтвердити" айді квитка
    @FXML
    private void onGetIDTicketButtonClick() {
        try { // Пробуємо отримати айді з вводу
            String textID = find_ticket_IDTicketTextField.getText();
            ticketID = Integer.parseInt(textID);

            try { // Якщо все введно коректно, пробуємо знайти квиток за цим айді
                selectedTicket = TicketDAO.findByID(ticketID);

                //  Якщо квиток знайдено, то з'явиться форма з даними
                if (selectedTicket != null && selectedTicket.getId() != 0){
                    changeVisibleItem(true);
                    find_ticket_idCruise_Label.setText(textID);
                    find_ticket_nameCruise_Label.setText(selectedTicket.getCruise().getName());
                    find_ticket_namePeople_Label.setText(selectedTicket.getSurname() + " " + selectedTicket.getFirstname());
                    find_ticket_company_Label.setText(selectedTicket.getCruise().getCompany());
                    find_ticket_liner_Label.setText(selectedTicket.getCruise().getShip());
                    find_ticket_price_Label.setText(String.valueOf(selectedTicket.getCruise().getPrice()));
                }
                else { // Якщо ж ні, то про це повідомить одна з помилок
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

    // Метод відкриття модульного вікна пошуку квитків
    public static void findTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(FindTicketController.class.getResource("/com/cruiseproject/windows/find-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 480, 270);
            window = new Stage();
            window.getIcons().add(new Image(Objects.requireNonNull(FindTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(FindTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(FindTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Знайти квиток");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод контролю видимості елементів форм пошуку
    private void changeVisibleItem(boolean mod){
        find_ticket_Label2.setVisible(mod);
        find_ticket_Label3.setVisible(mod);
        find_ticket_Label4.setVisible(mod);
        find_ticket_Label5.setVisible(mod);
        find_ticket_Label7.setVisible(mod);
        find_ticket_Label8.setVisible(mod);
        find_ticket_idCruise_Label.setVisible(mod);
        find_ticket_nameCruise_Label.setVisible(mod);
        find_ticket_namePeople_Label.setVisible(mod);
        find_ticket_company_Label.setVisible(mod);
        find_ticket_liner_Label.setVisible(mod);
        find_ticket_price_Label.setVisible(mod);

        find_ticket_Label1.setVisible(!mod);
        find_ticket_IDTicketTextField.setVisible(!mod);
        find_ticket_getIDTicketButton.setVisible(!mod);
    }
}
