package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FilterImpl implements Filter {

    final long MS_IN_HOUR = 3600000L;

    @Override
    public List<Flight> removeDepartureBeforeNow(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> resultList = new ArrayList<>();
        for (Flight flight : flights) {
            boolean isValid = true;
            List<Segment> segments = flight.getSegments();
            for (Segment segment : segments) {
                if (segment.getDepartureDate().isBefore(now)) {
                    isValid = false;
                }
            }
            if (isValid) {
                resultList.add(flight);
            }
        }
        return resultList;
    }

    @Override
    public List<Flight> removeArrivalBeforeDeparture(List<Flight> flights) {
        List<Flight> resultList = new ArrayList<>();
        for (Flight flight : flights) {
            boolean isValid = true;
            List<Segment> segments = flight.getSegments();
            for (Segment segment : segments) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    isValid = false;
                }
            }
            if (isValid) {
                resultList.add(flight);
            }
        }
        return resultList;
    }

    @Override
    public List<Flight> removeGroundTimeMoreThan(List<Flight> flights, int hours) {
        List<Flight> resultList = new ArrayList<>();
        for (Flight flight : flights) {
            boolean isValid = true;
            long groundTime = 0;
            List<Segment> segments = flight.getSegments();
            int numberOfSegs = segments.size();
            if (numberOfSegs > 1) {
                for (int i = 0; i < numberOfSegs - 1; i++) {
                    LocalDateTime start = segments.get(i).getArrivalDate();
                    LocalDateTime fin = segments.get(i + 1).getDepartureDate();
                    groundTime += ChronoUnit.MILLIS.between(start, fin);
                }
                if (groundTime > hours * MS_IN_HOUR) {
                    isValid = false;
                }
            }
            if (isValid) {
                resultList.add(flight);
            }
        }
        return resultList;
    }
}
