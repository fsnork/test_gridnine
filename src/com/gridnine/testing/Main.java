package com.gridnine.testing;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        List<Flight> list;
        FilterImpl filter = new FilterImpl();

        System.out.println("Get all flights except those which have departure time before now\n");
        list = filter.removeDepartureBeforeNow(flights);
        for (Flight flight : list) {
            System.out.println(flight);
        }
        System.out.println("\n----------------------------------------");

        System.out.println("Get all flights except those which have segments with arrival time before departure time\n");
        list = filter.removeArrivalBeforeDeparture(flights);
        for (Flight flight : list) {
            System.out.println(flight);
        }
        System.out.println("\n----------------------------------------");

        System.out.println("Get all flights except those which have ground time over 2 hours\n");
        list = filter.removeGroundTimeMoreThan(flights, 2);
        for (Flight flight : list) {
            System.out.println(flight);
        }
    }
}
