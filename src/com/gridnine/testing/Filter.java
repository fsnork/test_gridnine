package com.gridnine.testing;

import java.util.List;

public interface Filter {

    List<Flight> removeDepartureBeforeNow(List<Flight> flights);

    List<Flight> removeArrivalBeforeDeparture(List<Flight> flights);

    List<Flight> removeGroundTimeMoreThan(List<Flight> flights, int hours);
}
