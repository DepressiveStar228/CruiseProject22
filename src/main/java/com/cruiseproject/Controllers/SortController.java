package com.cruiseproject.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class SortController {
    @FXML
    private ChoiceBox<String> sort_cuises_companyChoiceBox;

    @FXML
    private ChoiceBox<String> sort_cuises_priceChoiceBox;

    @FXML
    private DatePicker sort_cuises_dateStartDatePicker;

    @FXML
    private DatePicker sort_cuises_dateFinishDatePicker;

    private static Stage window;
    boolean selectedDepartureDate = false;
    boolean selectedArrivalDate = false;


    public void initialize() {
        sort_cuises_dateStartDatePicker.setOnAction(event -> selectedDepartureDate = true);
        sort_cuises_dateFinishDatePicker.setOnAction(event -> selectedArrivalDate = true);

        //ArrayList<String> arrayList = CruiseDAO.getCompany();
        //ObservableList<String> nameCompanyItems = FXCollections.observableArrayList(arrayList);
        //sort_cuises_companyChoiceBox.setItems(nameCompanyItems);

        ObservableList<String> nameCompanyItems = FXCollections.observableArrayList("Дешеві", "Дорогі");
        sort_cuises_priceChoiceBox.setItems(nameCompanyItems);
    }

    @FXML
    private void onSortCruisesButtonClick() {
        LocalDate departureDate = sort_cuises_dateStartDatePicker.getValue();
        LocalDate arrivalDate = sort_cuises_dateFinishDatePicker.getValue();
        String company = sort_cuises_companyChoiceBox.getValue();
        String price = sort_cuises_priceChoiceBox.getValue();

        switch (checkCorrectData(departureDate, arrivalDate, company, price)) {
            case 1 -> window.close();
            case 2 -> {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Помилка");
                alert2.setHeaderText(null);
                alert2.setContentText("Ви не вибрали початок проміжку дати");
                alert2.showAndWait();
            }
            case 3 -> {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Помилка");
                alert3.setHeaderText(null);
                alert3.setContentText("Ви не вибрали кінець проміжку дати");
                alert3.showAndWait();
            }
            case 4 -> {
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Помилка");
                alert4.setHeaderText(null);
                alert4.setContentText("Початок та кінець подорожі не можуть співпадати");
                alert4.showAndWait();
            }
            case 5 -> {
                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setTitle("Помилка");
                alert5.setHeaderText(null);
                alert5.setContentText("Початок подорожі не може бути пізніше кінця");
                alert5.showAndWait();
            }
            default -> {
                int priceType;
                if (Objects.equals(price, "Дешеві")) { priceType = 1; }
                else { priceType = 2; }

                /*
                try {
                    ArrayList<Cruise> sortedCruises = CruiseDAO.sorting(company, priceType, departureDate, arrivalDate);

                    if (!sortedCruises.isEmpty()){
                        //медот добавления в ленту
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Помилка");
                        alert.setHeaderText(null);
                        alert.setContentText("Нічого не знайдено");
                        alert.showAndWait();
                    }
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Помилка в сортуванні");
                    alert.showAndWait();
                }
                 */

                window.close();
            }
        }
    }

    public static void sortCruises() {
        FXMLLoader fxmlLoader = new FXMLLoader(SortController.class.getResource("/com/cruiseproject/sort-cruise.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            window = new Stage();
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Сортування круїзів");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int checkCorrectData(LocalDate departureDate, LocalDate arrivalDate, String company, String price){
        if (!selectedDepartureDate && !selectedArrivalDate && company == null && price == null) { return 1; }
        else if (!selectedDepartureDate && selectedArrivalDate) { return 2; }
        else if (selectedDepartureDate && !selectedArrivalDate) { return 3; }
        else if (selectedDepartureDate && selectedArrivalDate && departureDate.isEqual(arrivalDate)) { return 4; }
        else if (selectedDepartureDate && selectedArrivalDate && departureDate.isAfter(arrivalDate)) { return 5; }
        else { return 6; }
    }
}
