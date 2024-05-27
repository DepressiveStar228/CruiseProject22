package com.cruiseproject.DAO;

import com.cruiseproject.Items.City;
import com.cruiseproject.Items.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Клас зв'язку програми з таблицею міст у БД
@Component("cityDao")
public class CityDAO {
    private static final Connection connection = PostgreConnection.getConnection(); // Отримуємо зв'язок

    // Метод повернення міста за його айді
    public static City findCityByID(int city_id) throws SQLException {
        City city = null;

        try { // Спроба знайти місто
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM public.cities WHERE city_id=?");
            preparedStatement.setInt(1,city_id);

            try (ResultSet cityData = preparedStatement.executeQuery()) {
                while (cityData.next()) {
                    city = new City();
                    city.setId(cityData.getInt("city_id"));
                    city.setName(cityData.getString("city_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e){ // Вивід інформації про помилку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return city;
    }

}