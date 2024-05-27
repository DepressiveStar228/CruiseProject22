package com.cruiseproject.Controllers;

import com.cruiseproject.DAO.CruiseDAO;
import com.cruiseproject.Items.Cruise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

// Клас контроллер сортування круїзів
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
    private boolean selectedDepartureDate = false;
    private boolean selectedArrivalDate = false;
    private HelloController helloController;

    // Метод ініціалізації модульного вікна для сортування
    public void initialize() throws SQLException {
        // Шлухачі подій вибору дат
        sort_cuises_dateStartDatePicker.setOnAction(event -> selectedDepartureDate = true);
        sort_cuises_dateFinishDatePicker.setOnAction(event -> selectedArrivalDate = true);
        ArrayList<String> arrayList = null;

        try { // Спроба отримати список всіх компаній
            arrayList = CruiseDAO.getCompany();
            ObservableList<String> nameCompanyItems1 = FXCollections.observableArrayList(arrayList);
            sort_cuises_companyChoiceBox.setItems(nameCompanyItems1); // Занесення до випадаючого списку вибору фільтрації за компаніями
        } catch (NullPointerException e){
            e.getMessage();
        }

        ObservableList<String> nameCompanyItems = FXCollections.observableArrayList("Дешеві", "Дорогі");
        sort_cuises_priceChoiceBox.setItems(nameCompanyItems); // Занесення до випадаючого списку вибору фільтрації за ціною
    }

    // Метод подія на клік "Застосувати налаштування"
    @FXML
    private void onSortCruisesButtonClick() throws IOException, SQLException {
        // Отримання даних сортування з форми
        LocalDate departureDate = sort_cuises_dateStartDatePicker.getValue();
        LocalDate arrivalDate = sort_cuises_dateFinishDatePicker.getValue();
        String company = sort_cuises_companyChoiceBox.getValue();
        String price = sort_cuises_priceChoiceBox.getValue();

        switch (checkCorrectData(departureDate, arrivalDate, company, price)) { // Вибір варіанта дії згідно результатіів перевірки даних
            case 1 -> { // Варіант, якщо нічого не було введено

                ArrayList<Cruise> cruises = CruiseDAO.getCruises();

                if (!cruises.isEmpty()){
                    helloController.setCruises(cruises);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Нічого не знайдено");
                    alert.showAndWait();
                }

                window.close();
            }
            case 2 -> { // Варіант, якщо не була вибрана дата початку подорожі, хоча дата кінця було
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Помилка");
                alert2.setHeaderText(null);
                alert2.setContentText("Ви не вибрали початок проміжку дати");
                alert2.showAndWait();
            }
            case 3 -> { // Варіант, якщо не була вибрана дата кінця подорожі, хоча дата початку було
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setTitle("Помилка");
                alert3.setHeaderText(null);
                alert3.setContentText("Ви не вибрали кінець проміжку дати");
                alert3.showAndWait();
            }
            case 4 -> { // Варіант, якщо дата початку та кінця подорожі це один і той самий день
                Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Помилка");
                alert4.setHeaderText(null);
                alert4.setContentText("Початок та кінець подорожі не можуть співпадати");
                alert4.showAndWait();
            }
            case 5 -> { // Варіант, якщо дата початку подорожі пізніше її кінця
                Alert alert5 = new Alert(Alert.AlertType.ERROR);
                alert5.setTitle("Помилка");
                alert5.setHeaderText(null);
                alert5.setContentText("Початок подорожі не може бути пізніше кінця");
                alert5.showAndWait();
            }
            default -> { // Варіант, якщо всі перевірки пройдені
                int priceType;
                if (Objects.equals(price, "Дешеві")) { priceType = 1; }
                else { priceType = 2; }

                try { // Пробуємо відсортувати за отриманими даними круїзи
                    ArrayList<Cruise> sortedCruises = CruiseDAO.sorting(
                            company,
                            priceType,
                            departureDate != null ? departureDate.toString() : null,
                            arrivalDate != null ? arrivalDate.toString() : null
                    );

                    if (!sortedCruises.isEmpty()){ // Якщо щось було відсортовано, то заносимо цей список до стрічки
                        helloController.setCruises(sortedCruises);
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Помилка");
                        alert.setHeaderText(null);
                        alert.setContentText("Нічого не знайдено");
                        alert.showAndWait();
                    }
                } catch (NullPointerException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Нічого не знайдено");
                    alert.showAndWait();
                }

                window.close();
            }
        }
    }

    // Метод відкриття модульного вікна сортування круїзів
    public static void sortCruises(HelloController helloController) {
        FXMLLoader fxmlLoader = new FXMLLoader(SortController.class.getResource("/com/cruiseproject/windows/sort-cruise.fxml"));

        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            SortController controller = fxmlLoader.getController();
            controller.setHelloController(helloController);
            window = new Stage();
            window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-16x16.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-24x24.png"))));
            window.getIcons().add(new Image(Objects.requireNonNull(InfoController.class.getResourceAsStream("/com/cruiseproject/icons/icon-cruise-32x32.png"))));
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Сортування круїзів");
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод отримання екземпляра класа головного вікна програми
    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    // Метод перевірки даних сортування
    private int checkCorrectData(LocalDate departureDate, LocalDate arrivalDate, String company, String price){
        if (!selectedDepartureDate && !selectedArrivalDate && company == null && price == null) { return 1; }
        else if (!selectedDepartureDate && selectedArrivalDate) { return 2; }
        else if (selectedDepartureDate && !selectedArrivalDate) { return 3; }
        else if (selectedDepartureDate && selectedArrivalDate && departureDate.isEqual(arrivalDate)) { return 4; }
        else if (selectedDepartureDate && selectedArrivalDate && departureDate.isAfter(arrivalDate)) { return 5; }
        else { return 6; }
    }
}
