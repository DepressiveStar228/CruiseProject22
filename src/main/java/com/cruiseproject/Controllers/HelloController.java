package com.cruiseproject.Controllers;


import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.Items.City;
import com.cruiseproject.Items.Cruise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private ScrollPane listCruises = new ScrollPane();

    @FXML
    private VBox itemCardCruise;

    private ArrayList<Cruise> cruises;

    public void initialize() throws IOException, SQLException {
        listCruises.setFitToWidth(true);

        cruises = CruiseDAO.getCruises();
        setCruises(cruises);
    }

    @FXML
    protected void onAddTicketClick(){
        AddTicketController.addTicket();
    }

    @FXML
    protected void onFindClick(){
       FindTicketController.findTicket();
    }

    @FXML
    protected void onDeleteTicketClick() {
        DeleteTicketController.delTicket();
    }

    @FXML
    protected void onSortCruiseClick() {
        SortController.sortCruises(this);
    }

    @FXML
    protected void onAboutUsClick() { InfoController.info(true); }

    @FXML
    protected void onInformationClick() {
        InfoController.info(false);
    }

    private void addCruiseScrollPane(Cruise cruise) throws IOException {
        FXMLLoader loader = new FXMLLoader(AddCruiseCardController.class.getResource("/com/cruiseproject/windows/cruise-card-children.fxml"));
        Pane card = loader.load();

        AddCruiseCardController controller = loader.getController();
        controller.setInfoCruise(cruise);

        itemCardCruise.getChildren().add(card);
    }

    public void setCruises(ArrayList<Cruise> cruises) throws IOException {
        itemCardCruise.getChildren().clear();
        for (Cruise cruise : cruises) {
            addCruiseScrollPane(cruise);
        }
    }
}