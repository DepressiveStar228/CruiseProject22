package com.cruiseproject.Items;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cruise {
    private int id;
    private int price;
    private int freeSeats;
    private ArrayList<City> cruiseRoute;
    private String company;
    private String departure;
    private String arrival;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Cruise(){};

    public Cruise(int id, int price, int freeSeats, ArrayList<City> cruiseRoute, String company, String departure, String arrival) {
        this.id = id;
        this.price = price;
        this.freeSeats = freeSeats;
        this.cruiseRoute = cruiseRoute;
        this.company = company;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getRoute() {
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < cruiseRoute.size(); i++) {
            route.append(cruiseRoute.get(i).getName());
            if (i < cruiseRoute.size() - 1) {
                route.append(" -> ");
            }
        }
        return route.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public ArrayList<City> getCruiseRoute() {
        return cruiseRoute;
    }

    public void setCruiseRoute(ArrayList<City> cruiseRoute) {
        this.cruiseRoute = cruiseRoute;
    }
}
