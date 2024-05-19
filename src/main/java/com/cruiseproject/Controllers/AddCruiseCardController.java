package com.cruiseproject.Controllers;

import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.Items.Cruise;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AddCruiseCardController {

    @FXML
    private Label sort_childrenID_Label;

    @FXML
    private Label sort_childrenNameCruise_Label;

    @FXML
    private Label sort_childrenDate_Label;

    @FXML
    private Label sort_childrenNameCompany_Label;

    @FXML
    private Label sort_childrenLiner_Label;

    @FXML
    private Label sort_childrenPrice_Label;

    public void setInfoCruise(Cruise cruise) {
        sort_childrenID_Label.setText(String.valueOf(cruise.getId()));
        sort_childrenNameCruise_Label.setText(cruise.getName());
        sort_childrenDate_Label.setText(cruise.getDeparture() + " - " + cruise.getArrival());
        sort_childrenNameCompany_Label.setText(cruise.getCompany());
        sort_childrenLiner_Label.setText(cruise.getShip());
        sort_childrenPrice_Label.setText(String.valueOf(cruise.getPrice()));
    }
}
