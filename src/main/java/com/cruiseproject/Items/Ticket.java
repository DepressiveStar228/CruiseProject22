package com.cruiseproject.Items;

// Класс сутність Квиток
public class Ticket {

    private int id;
    private Cruise cruise;
    private int seatNum;
    private String surname;
    private String firstname;

    public Ticket(){}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // Конструктор класу
    public Ticket(int id, Cruise cruise, int seatNum, String surname, String firstname) {
        this.id = id;
        this.cruise = cruise;
        this.seatNum = seatNum;
        this.surname = surname;
        this.firstname = firstname;
    }

    public Ticket(int ticketId, Object cruise) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", cruise=" + cruise +
                ", seatNum=" + seatNum +
                '}';
    }
}