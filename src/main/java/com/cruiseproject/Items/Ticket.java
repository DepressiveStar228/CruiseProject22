package com.cruiseproject.Items;

public class Ticket {
    private int id;
    private Cruise cruise;
    private int seatNum;

    public Ticket(){}

    public Ticket(int id, Cruise cruise, int seatNum) {
        this.id = id;
        this.cruise = cruise;
        this.seatNum = seatNum;
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
