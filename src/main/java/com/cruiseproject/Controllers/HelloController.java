package com.cruiseproject.Controllers;


import com.cruiseproject.Items.Cruise;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private ScrollPane scrollPane = new ScrollPane();

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
        SortController.sortCruises();
    }

    @FXML
    protected void onAboutUsClick() { InfoController.info(true); }

    @FXML
    protected void onInformationClick() {
        InfoController.info(false);
    }
}