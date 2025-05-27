package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGreatCircleDistance {

    @Test
    @DisplayName("<Alaskan>: Random value test for accuracy and correct calculation")
    public void test() {
        double y = 10.0;
        assertEquals(0.17453292519943295, GreatCircleDistance.toRadians(y));
    }

    @Test
    @DisplayName("awohara: calculate method returns the expected distance value.")
    public void testCalculate() {

        testCalculate1();
        testCalculate2();
        testCalculate3();
    }

    private void testCalculate1() {

        GeographicCoordinate coordinate1 = new GeographicCoordinate(126.0,37.56);
        GeographicCoordinate coordinate2 = new GeographicCoordinate(-153.98,65.07);
        double earthRadius = 4.5;

        long distance = GreatCircleDistance.calculate(coordinate1,coordinate2, earthRadius);

        assertEquals(4, distance);
    }

    private void testCalculate2() {
        GeographicCoordinate coordinate1 = new GeographicCoordinate(27.16, 1.49);
        GeographicCoordinate coordinate2 = new GeographicCoordinate(-0.0, 0.0);
        double earthRadius = 4.5;

        long distance = GreatCircleDistance.calculate(coordinate1,coordinate2, earthRadius);

        assertEquals(2, distance);
    }

    private void testCalculate3() {

        GeographicCoordinate coordinate1 = new GeographicCoordinate(-81.577507, 28.419048);
        GeographicCoordinate coordinate2 = new GeographicCoordinate(-81.560062, 28.360032);
        double earthRadius = 3671.0;

        long distance = GreatCircleDistance.calculate(coordinate1,coordinate2, earthRadius);

        assertEquals(4, distance);
    }

}