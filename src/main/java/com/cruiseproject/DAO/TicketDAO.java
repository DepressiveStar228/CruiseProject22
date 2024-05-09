package com.cruiseproject.DAO;

import com.cruiseproject.Items.Cruise;
import com.cruiseproject.Items.Ticket;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class TicketDAO {
    private static Connection connection = PostgreConnection.getConnection();

    public static Ticket findByID(int ticket_id) throws SQLException {
        Ticket ticket = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM public.tickets WHERE ticket_id=?");
            preparedStatement.setInt(1 , ticket_id);
            ResultSet ticketData = preparedStatement.executeQuery();
            ticket = new Ticket();
            ticket.setId(ticketData.getInt("ticket_id"));
            ticket.setSeatNum(ticketData.getInt("seat_num"));
            ticket.setCruise(CruiseDAO.findCruiseByID(ticketData.getInt("cruise_id")));
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }

        return ticket;
    }
    public static boolean removeTicketByID(int ticket_id) throws SQLException {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM public.tickets WHERE ticket_id = ?");
            preparedStatement.setInt(1,ticket_id);
            preparedStatement.executeUpdate();
            return true;
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }
        return false;
    }
    public static void  addTicket(int cruise_id) throws SQLException {
        try {
            Cruise cruise = CruiseDAO.findCruiseByID(cruise_id);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.tickets (cruise, seat_num) VALUES (?, ?)");
            preparedStatement.setInt(1, cruise_id);
            preparedStatement.setInt(2,cruise.getFreeSeats());
            CruiseDAO.decrementFreeSeats(cruise_id);
            preparedStatement.executeUpdate();
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Не вдалося зв'язатися з базою даних");
            alert.showAndWait();
        }
    }
    public static ArrayList<Ticket> getTickets() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from public.tickets");
        ResultSet tickets = preparedStatement.executeQuery();
        ArrayList<Ticket> ticketList = new ArrayList<>();
        while(tickets.next()){
            Ticket currTicket = new Ticket();
            currTicket.setId(tickets.getInt("ticket_id"));
            currTicket.setSeatNum(tickets.getInt("seat_num"));
            currTicket.setCruise(CruiseDAO.findCruiseByID(tickets.getInt("cruise_id")));
            ticketList.add(currTicket);
        }
        return ticketList;
    }


}
