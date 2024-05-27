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


// Клас контроллер всіх функцій головної програми
public class HelloController {
    @FXML
    private ScrollPane listCruises = new ScrollPane();

    @FXML
    private VBox itemCardCruise;

    private ArrayList<Cruise> cruises;

    // Метод ініціалізації головного вікна програми
    public void initialize() throws IOException{
        listCruises.setFitToWidth(true);

        try { // Спроба отримати список всіх круїзів
            cruises = CruiseDAO.getCruises();
            setCruises(cruises);
        } catch (SQLException | NullPointerException e){
            setCruises(null);
        }
    }

    // Метод подія на клік кнопки "Замовити квиток"
    @FXML
    protected void onAddTicketClick(){
        AddTicketController.addTicket();
    }

    // Метод подія на клік кнопки "Знайти квиток"
    @FXML
    protected void onFindClick(){
       FindTicketController.findTicket();
    }

    // Метод подія на клік кнопки "Видалити квиток"
    @FXML
    protected void onDeleteTicketClick() {
        DeleteTicketController.delTicket();
    }

    // Метод подія на клік кнопки "Сортувати круїзи"
    @FXML
    protected void onSortCruiseClick() {
        SortController.sortCruises(this);
    }

    // Метод подія на клік кнопки "Про нас"
    @FXML
    protected void onAboutUsClick() { InfoController.info(true); }

    // Метод подія на клік кнопки "Довідка"
    @FXML
    protected void onInformationClick() {
        InfoController.info(false);
    }

    // Метод додавання карток круїзів до стрічки на головному вікні
    private void addCruiseScrollPane(Cruise cruise) throws IOException {
        FXMLLoader loader = new FXMLLoader(AddCruiseCardController.class.getResource("/com/cruiseproject/windows/cruise-card-children.fxml"));
        Pane card = loader.load();

        AddCruiseCardController controller = loader.getController();
        controller.setInfoCruise(cruise);

        itemCardCruise.getChildren().add(card);
    }

    // Метод оновлення стрічки карток круїзів на головному вікні
    public void setCruises(ArrayList<Cruise> cruises) throws IOException {
        itemCardCruise.getChildren().clear();

        if (cruises != null) {
            for (Cruise cruise : cruises) {
                addCruiseScrollPane(cruise);
            }
        }
    }
}