package com.cruiseproject.Controllers;

import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.DAO.TicketDAO;
import com.cruiseproject.Items.Cruise;
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
    private Label find_ticket_Label6;

    @FXML
    private Label find_ticket_Label7;

    @FXML
    private Label find_ticket_idCruise_Label;

    @FXML
    private Label find_ticket_nameCruise_Label;

    @FXML
    private Label find_ticket_namePeople_Label;

    @FXML
    private Label find_ticket_company_Label;

    @FXML
    private Label find_ticket_lainer_Label;

    @FXML
    private Label find_ticket_price_Label;

    @FXML
    private TextField find_ticket_IDTicketTextField;

    @FXML
    private Button find_ticket_getIDTicketButton;

    private static Stage window;
    private int ticketID;
    private Ticket selectedTicket;


    public void initialize() {
        changeVisibleItem(false);
    }

    @FXML
    private void onGetIDTicketButtonClick() {
        try {
            String textID = find_ticket_IDTicketTextField.getText();
            ticketID = Integer.parseInt(textID);

            try {
                selectedTicket = TicketDAO.findByID(ticketID);

                if (selectedTicket != null){
                    changeVisibleItem(true);
                    find_ticket_idCruise_Label.setText(textID);
                    find_ticket_nameCruise_Label.setText("");
                    find_ticket_namePeople_Label.setText("");
                    find_ticket_company_Label.setText("");
                    find_ticket_lainer_Label.setText("");
                    find_ticket_price_Label.setText("");
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

    public static void findTicket(){
        FXMLLoader fxmlLoader = new FXMLLoader(FindTicketController.class.getResource("/com/cruiseproject/find-ticket.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 480, 270);
            window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Знайти квиток");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeVisibleItem(boolean mod){
        find_ticket_Label2.setVisible(mod);
        find_ticket_Label3.setVisible(mod);
        find_ticket_Label4.setVisible(mod);
        find_ticket_Label5.setVisible(mod);
        find_ticket_Label6.setVisible(mod);
        find_ticket_Label7.setVisible(mod);
        find_ticket_idCruise_Label.setVisible(mod);
        find_ticket_nameCruise_Label.setVisible(mod);
        find_ticket_namePeople_Label.setVisible(mod);
        find_ticket_company_Label.setVisible(mod);
        find_ticket_lainer_Label.setVisible(mod);
        find_ticket_price_Label.setVisible(mod);

        find_ticket_Label1.setVisible(!mod);
        find_ticket_IDTicketTextField.setVisible(!mod);
        find_ticket_getIDTicketButton.setVisible(!mod);
    }
}
