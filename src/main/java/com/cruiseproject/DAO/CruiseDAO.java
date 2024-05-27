package com.cruiseproject.DAO;

import com.cruiseproject.Items.City;
import com.cruiseproject.Items.Cruise;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.transform.Result;
import java.net.ConnectException;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Клас зв'язку програми з таблицею круїзів у БД
public class CruiseDAO {
    private static final Connection connection = PostgreConnection.getConnection(); // Отримуємо зв'язок

    // Метод знаходження круїза за його айді
    public static Cruise findCruiseByID(int cruise_id) throws SQLException {
        Cruise cruise = null;

        try { // Спроба знайти круїз
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM public.cruise WHERE cruise_id=?");
            preparedStatement.setInt(1,cruise_id);
            ResultSet cruiseData = preparedStatement.executeQuery();
            cruise = new Cruise();
            while(cruiseData.next()) {
                cruise.setId(cruiseData.getInt("cruise_id"));
                cruise.setPrice(cruiseData.getInt("price"));
                cruise.setFreeSeats(cruiseData.getInt("free_seats"));
                cruise.setCruiseRoute(getCities(cruise_id));
                cruise.setArrival(cruiseData.getString("arrival_date"));
                cruise.setDeparture(cruiseData.getString("departure_date"));
                cruise.setCompany(cruiseData.getString("company_name"));
                cruise.setName(cruiseData.getString("name"));
                cruise.setShip(cruiseData.getString("ship"));
            }
        } catch (NullPointerException e){  // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return cruise;
    }

    // Метод повернення списку міст круїза за його айді
    public static ArrayList<City> getCities(int cruise_id) throws SQLException {
        ArrayList<City> cityResult = null;

        try { // Спроба знайти міста
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT cruise_id, city_id, city_position FROM public.cruise2cities where cruise_id = ?");
            preparedStatement.setInt(1,cruise_id);
            ResultSet citiesData = preparedStatement.executeQuery();
            cityResult = new ArrayList<>();
            while(citiesData.next()){
                int city_id = citiesData.getInt("city_id");
                int city_position = citiesData.getInt("city_position");
                City currentCity = CityDAO.findCityByID(city_id);
                cityResult.add(city_position,currentCity);
            }
        } catch (NullPointerException e){ // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return cityResult;
    }

    // Метод повернення списку круїзів
    public static ArrayList<Cruise> getCruises() throws SQLException {
        ArrayList<Cruise> cruiseResult = null;

        try { // Спроба знайти круїзи
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM public.cruise");

            ResultSet cruiseData = preparedStatement.executeQuery();
            cruiseResult = new ArrayList<>();
            while(cruiseData.next()){
                Cruise cruise = new Cruise();
                cruise.setId(cruiseData.getInt("cruise_id"));
                cruise.setPrice(cruiseData.getInt("price"));
                cruise.setFreeSeats(cruiseData.getInt("free_seats"));
                cruise.setCruiseRoute(getCities(cruiseData.getInt("cruise_id")));
                cruise.setArrival(cruiseData.getString("arrival_date"));
                cruise.setDeparture(cruiseData.getString("departure_date"));
                cruise.setCompany(cruiseData.getString("company_name"));
                cruise.setName(cruiseData.getString("name"));
                cruise.setShip(cruiseData.getString("ship"));
                cruiseResult.add(cruise);
            }
        } catch (NullPointerException e){ // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return cruiseResult;
    }

    // Метод повернення списку компаній, що надають послуги круїзів в даний момент
    public static ArrayList<String> getCompany() throws SQLException {
        ArrayList<String> companyResult = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT DISTINCT company_name FROM public.cruise");

            ResultSet companyData = preparedStatement.executeQuery();
            companyResult = new ArrayList<>();
            while(companyData.next()){
                String companyName = companyData.getString("company_name");
                companyResult.add(companyName);
            }
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return companyResult;
    }

    // Метод бронювання одно місця в круїзі за його айді
    public static void decrementFreeSeats(int cruise_id) throws SQLException {
        try {// Спроба оновити місця в круїзі
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE public.cruise SET free_seats = free_seats - 1 WHERE cruise_id = ?");
            preparedStatement.setInt(1 , cruise_id);
            preparedStatement.executeUpdate();
        } catch (NullPointerException e){ // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }
    }

    // Метод сортування круїзів за параметрами
    public static ArrayList<Cruise> sorting(String company, Integer priceType, String departureDate, String arrivalDate) {
        ArrayList<Cruise> cruises = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM public.cruise WHERE 1=1"); // Початок запиту сортування

        if (company != null && !company.isEmpty()) { // Якщо передана назва компанії є та не пуста, то додаємо в запит і її
            queryBuilder.append(" AND company_name = ?");
        }

        // Якщо передані дати подорожі є та не пусті, то додаємо в запит і їх
        if (departureDate != null && !departureDate.isEmpty() && arrivalDate != null && !arrivalDate.isEmpty()) {
            queryBuilder.append(" AND departure_date >= TO_DATE(?, 'YYYY-MM-DD') AND arrival_date <= TO_DATE(?, 'YYYY-MM-DD')");
        }

        // Якщо переданий парамент сортування за ціною є та не пустий, то додаємо в запит і його
        if (priceType != null && (priceType == 1 || priceType == 2)) {
            queryBuilder.append(" ORDER BY price");
            if (priceType == 2) {
                queryBuilder.append(" DESC");
            }
        }

        try { // Спроба сортувати круїзи
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            if (company != null && !company.isEmpty()) {
                statement.setString(parameterIndex++, company);
            }
            if (departureDate != null && !departureDate.isEmpty() && arrivalDate != null && !arrivalDate.isEmpty()) {
                statement.setString(parameterIndex++, departureDate);
                statement.setString(parameterIndex, arrivalDate);
            }

            ResultSet cruiseData = statement.executeQuery();

            while (cruiseData.next()) { // Занесення отриманих відсортованих круїзів до списку
                Cruise cruise = new Cruise();
                cruise.setId(cruiseData.getInt("cruise_id"));
                cruise.setPrice(cruiseData.getInt("price"));
                cruise.setFreeSeats(cruiseData.getInt("free_seats"));
                cruise.setCruiseRoute(getCities(cruiseData.getInt("cruise_id")));
                cruise.setArrival(cruiseData.getString("arrival_date"));
                cruise.setDeparture(cruiseData.getString("departure_date"));
                cruise.setCompany(cruiseData.getString("company_name"));
                cruise.setName(cruiseData.getString("name"));
                cruise.setShip(cruiseData.getString("ship"));
                cruises.add(cruise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) { // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return cruises;
    }
}