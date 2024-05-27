package com.cruiseproject.Controllers;
import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.DAO.TicketDAO;
import com.cruiseproject.Items.Cruise;
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

// Клас контроллер замовлення квитків
public class AddTicketController {
    @FXML
    private Label add_ticket_Label1;

    @FXML
    private Label add_ticket_Label2;

    @FXML
    private Label add_ticket_Label3;

    @FXML
    private Label add_ticket_Label4;

    @FXML
    private Label add_ticket_Label5;

    @FXML
    private Label add_ticket_idCruise_Label;

    @FXML
    private Label add_ticket_nameCruise_Label;

    @FXML
    private TextField add_ticket_nameTextField;

    @FXML
    private TextField add_ticket_secNameTextField;

    @FXML
    private Button add_ticketFinishButton;

    @FXML
    private TextField add_ticket_IDCruiseTextField;

    @FXML
    private Button add_ticket_getIDCruiseButton;

    private static Stage window;
    private int cruiseID;
    private Cruise selectedCruise;


    // Метод ініціалізації модульного вікна для замовлення
    public void initialize() {
        changeVisibleItem(false);
    }

    // Метод подія на клік "Підтвердити" айді круїзу
    @FXML
    private void onGetIDCruiseButtonClick() {
        try { // Пробуємо отримати айді з вводу
            String textID = add_ticket_IDCruiseTextField.getText();
            cruiseID = Integer.parseInt(textID);

            try { // Якщо все введно коректно, пробуємо знайти круїз за цим айді
                selectedCruise = CruiseDAO.findCruiseByID(cruiseID);

                //  Якщо круїз знайдено, то з'явиться форма вводу особистих даних
                if (selectedCruise != null && selectedCruise.getId() != 0){
                    changeVisibleItem(true);
                    add_ticket_idCruise_Label.setText(textID);
                    add_ticket_nameCruise_Label.setText(selectedCruise.getName());
                }
                else {  // Якщо ж ні, то про це повідомить одна з помилок
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Круїз за даним ID не знайдено");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Помилка в знаходженні круїза");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Некоректний ID круїзу");
            alert.showAndWait();
        }
    }

    // Метод подія на клік "Замовити квиток"
    @FXML
    private void onFinishButtonClick() {
        try { // Якщо всі дані отримуються добре, то квиток оформлюється
            TicketDAO.addTicket(cruiseID, add_ticket_nameTextField.getText() , add_ticket_secNameTextField.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успішне замовлення");
            alert.setHeaderText(null);
            alert.setContentText("Квиток успішно замовлено. ID: " + TicketDAO.getLastID());
            alert.showAndWait();
            window.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод відкриття модульного вікна замовлення квитків
    public static void addTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(AddTicketController.class.getResource("/com/cruiseproject/windows/add-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 330);
            window = new Stage();
            window.getIcons().add(new Image(Objects.requireNonNull(AddTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(AddTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(AddTicketController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Додати значення до Array List");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод контролю видимості елементів форм замовлення
    private void changeVisibleItem(boolean mod){
        add_ticket_Label2.setVisible(mod);
        add_ticket_Label3.setVisible(mod);
        add_ticket_Label4.setVisible(mod);
        add_ticket_Label5.setVisible(mod);
        add_ticket_idCruise_Label.setVisible(mod);
        add_ticket_nameCruise_Label.setVisible(mod);
        add_ticket_nameTextField.setVisible(mod);
        add_ticket_secNameTextField.setVisible(mod);
        add_ticketFinishButton.setVisible(mod);

        add_ticket_Label1.setVisible(!mod);
        add_ticket_IDCruiseTextField.setVisible(!mod);
        add_ticket_getIDCruiseButton.setVisible(!mod);
    }
}