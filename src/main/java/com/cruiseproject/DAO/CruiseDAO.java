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

public class CruiseDAO {
    private static final Connection connection = PostgreConnection.getConnection();

    public static Cruise findCruiseByID(int cruise_id) throws SQLException {
        Cruise cruise = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM public.cruise WHERE city_id=?");
            preparedStatement.setInt(1,cruise_id);
            ResultSet cruiseData = preparedStatement.executeQuery();
            cruise = new Cruise();
            cruise.setId(cruiseData.getInt("cruise_id"));
            cruise.setPrice(cruiseData.getInt("price"));
            cruise.setFreeSeats(cruiseData.getInt("free_seats"));
            cruise.setCruiseRoute(getCities(cruise_id));
            cruise.setArrival(cruiseData.getString("arrival_date"));
            cruise.setDeparture(cruiseData.getString("departure_date"));
            cruise.setCompany(cruiseData.getString("company_name"));
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return cruise;
    }
    public static ArrayList<City> getCities(int cruise_id) throws SQLException {
        PreparedStatement preparedStatement =
        connection.prepareStatement("SELECT cruise_id, city_id, city_position FROM public.cruise2cities where cruise_id = ?");
        preparedStatement.setInt(1,cruise_id);
        ResultSet citiesData = preparedStatement.executeQuery();
        ArrayList <City> cityResult = new ArrayList<>();
        while(citiesData.next()){
            int city_id = citiesData.getInt("city_id");
            int city_position = citiesData.getInt("city_position");
            City currentCity = CityDAO.findCityByID(city_id);
            cityResult.add(city_position,currentCity);
        }
        return cityResult;
    }
    public static void decrementFreeSeats(int cruise_id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE public.cruise SET free_seats = free_seats - 1 WHERE cruise_id = ?");
        preparedStatement.setInt(1 , cruise_id);
        preparedStatement.executeUpdate();
    }
    public static ArrayList<Cruise> sorting(String company, Integer priceType, String departureDate, String arrivalDate) {
        ArrayList<Cruise> cruises = new ArrayList<>();


        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM public.cruise WHERE 1=1");


        if (company != null && !company.isEmpty()) {
            queryBuilder.append(" AND company_name = ?");
        }

        if (departureDate != null && !departureDate.isEmpty() && arrivalDate != null && !arrivalDate.isEmpty()) {
            queryBuilder.append(" AND departure_date >= ? AND arrival_date <= ?");
        }

        if (priceType != null && (priceType == 1 || priceType == 2)) {
            queryBuilder.append(" ORDER BY price");
            if (priceType == 2) {
                queryBuilder.append(" DESC");
            }
        }

        try {

            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());

            int parameterIndex = 1;
            if (company != null && !company.isEmpty()) {
                statement.setString(parameterIndex++, company);
            }
            if (departureDate != null && !departureDate.isEmpty() && arrivalDate != null && !arrivalDate.isEmpty()) {
                statement.setString(parameterIndex++, departureDate);
                statement.setString(parameterIndex, arrivalDate);
            }


            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                Cruise cruise = new Cruise();
                cruise.setPrice(resultSet.getInt("price"));
                cruise.setFreeSeats(resultSet.getInt("free_seats"));
                cruise.setCompany(resultSet.getString("company_name"));
                cruise.setDeparture(resultSet.getString("departure_date"));
                cruise.setArrival(resultSet.getString("arrival_date"));
                cruises.add(cruise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cruises;
    }
}
