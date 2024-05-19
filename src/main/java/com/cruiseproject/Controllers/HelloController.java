package com.cruiseproject.Controllers;


import com.cruiseproject.Items.City;
import com.cruiseproject.Items.Cruise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private ScrollPane listCruises = new ScrollPane();

    @FXML
    private VBox itemCardCruise;

    private ArrayList<Cruise> cruises;

    public void initialize() throws IOException {
        listCruises.setFitToWidth(true);

        //Тестовое
        ArrayList<City> cruiseRoute = new ArrayList<>();
        Cruise cruise1 = new Cruise(1, 10000, 60, cruiseRoute, "Компания 1", "01.05.2000", "20.05.2000", "Турбоход", "Поездка мечты");
        Cruise cruise2 = new Cruise(2, 10000, 60, cruiseRoute, "Компания 2", "01.05.2000", "20.05.2000", "Турбоход", "Поездка мечты");
        Cruise cruise3 = new Cruise(3, 10000, 60, cruiseRoute, "Компания 3", "01.05.2000", "20.05.2000", "Турбоход", "Поездка мечты");
        Cruise cruise4 = new Cruise(4, 10000, 60, cruiseRoute, "Компания 4", "01.05.2000", "20.05.2000", "Турбоход", "Поездка мечты");

        cruises = new ArrayList<>(); //получить список всех круизов с БД нужно
        cruises.add(cruise1);
        cruises.add(cruise2);
        cruises.add(cruise3);
        cruises.add(cruise4);

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