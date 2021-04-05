package com.gridnine.testing;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilterImplTest {

    private List<Flight> full;
    private List<Flight> test;
    private final FilterImpl filter = new FilterImpl();
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;
    private Flight flight4;
    private Flight flight5;
    private Flight flight6;

    @Before
    public void setUp() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        full = new ArrayList<>(FlightBuilder.createFlights());
        test = new ArrayList<>();
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        Method method = FlightBuilder.class.getDeclaredMethod("createFlight", LocalDateTime[].class);
        method.setAccessible(true);

        // I don't think this is the best way of creating flights for testing,
        // but I'm not allowed to refactor the FlightBuilder class, as I understand
        flight1 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow, threeDaysFromNow.plusHours(2)});
        flight2 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)});
        flight3 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow.minusDays(6), threeDaysFromNow});
        flight4 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow, threeDaysFromNow.minusHours(6)});
        flight5 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)});
        flight6 = (Flight) method.invoke(null, (Object) new LocalDateTime[] {threeDaysFromNow, threeDaysFromNow.plusHours(2),
                threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)});
    }

    @After
    public void clearList() {
        full.clear();
    }

    @Test
    public void removeDepartureBeforeNow() {
        test.add(flight1);
        test.add(flight2);
        test.add(flight4);
        test.add(flight5);
        test.add(flight6);
        List<Flight> expected = filter.removeDepartureBeforeNow(full);
        Assert.assertEquals(expected.toString(), test.toString());
    }

    @Test
    public void removeArrivalBeforeDeparture() {
        test.add(flight1);
        test.add(flight2);
        test.add(flight3);
        test.add(flight5);
        test.add(flight6);
        List<Flight> expected = filter.removeArrivalBeforeDeparture(full);
        Assert.assertEquals(expected.toString(), test.toString());
    }

    @Test
    public void removeGroundTimeMoreThan() {
        test.add(flight1);
        test.add(flight2);
        test.add(flight3);
        test.add(flight4);
        List<Flight> expected = filter.removeGroundTimeMoreThan(full, 2);
        Assert.assertEquals(expected.toString(), test.toString());
    }
}