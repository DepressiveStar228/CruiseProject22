package com.cruiseproject.DAO;

import com.cruiseproject.Items.City;
import com.cruiseproject.Items.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("cityDao")
public class CityDAO {
    private static final Connection connection = PostgreConnection.getConnection();
    public static City findCityByID(int city_id) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM public.cities WHERE city_id=?");
        preparedStatement.setInt(1,city_id);
        ResultSet cityData = preparedStatement.executeQuery();
        City city = new City();
        city.setId(cityData.getInt("city_id"));
        city.setName(cityData.getString("city_name"));
        return city;
    }

}
