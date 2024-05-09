package com.cruiseproject.Controllers;
import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.DAO.TicketDAO;
import com.cruiseproject.Items.Cruise;
import com.cruiseproject.Items.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
    private Label add_ticket_Label6;

    @FXML
    private Label add_ticket_idCruise_Label;

    @FXML
    private Label add_ticket_nameCruise_Label;

    @FXML
    private TextField add_ticket_nameTextField;

    @FXML
    private TextField add_ticket_secNameTextField;

    @FXML
    private TextField add_ticket_thirNameTextField;

    @FXML
    private Button add_ticketFinishButton;

    @FXML
    private TextField add_ticket_IDCruiseTextField;

    @FXML
    private Button add_ticket_getIDCruiseButton;

    private static Stage window;
    private int cruiseID;
    private Cruise selectedCruise;


    public void initialize() {
        changeVisibleItem(false);
    }

    @FXML
    private void onGetIDCruiseButtonClick() {
        try {
            String textID = add_ticket_IDCruiseTextField.getText();
            cruiseID = Integer.parseInt(textID);

            try {
                selectedCruise = CruiseDAO.findCruiseByID(cruiseID);

                if (selectedCruise != null){
                    changeVisibleItem(true);
                    add_ticket_idCruise_Label.setText(textID);
                }
                else {
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

    @FXML
    private void onFinishButtonClick() {
        try {
            TicketDAO.addTicket(cruiseID);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успішне замовлення");
            alert.setHeaderText(null);
            alert.setContentText("Замовлення " + selectedCruise.getCruiseRoute() + " успішно оформлено.");
            alert.showAndWait();
            window.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(AddTicketController.class.getResource("/com/cruiseproject/add-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 330);
            window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Додати значення до Array List");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeVisibleItem(boolean mod){
        add_ticket_Label2.setVisible(mod);
        add_ticket_Label3.setVisible(mod);
        add_ticket_Label4.setVisible(mod);
        add_ticket_Label5.setVisible(mod);
        add_ticket_Label6.setVisible(mod);
        add_ticket_idCruise_Label.setVisible(mod);
        add_ticket_nameCruise_Label.setVisible(mod);
        add_ticket_nameTextField.setVisible(mod);
        add_ticket_secNameTextField.setVisible(mod);
        add_ticket_thirNameTextField.setVisible(mod);
        add_ticketFinishButton.setVisible(mod);

        add_ticket_Label1.setVisible(!mod);
        add_ticket_IDCruiseTextField.setVisible(!mod);
        add_ticket_getIDCruiseButton.setVisible(!mod);
    }
}
