package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGeographicCoordinate {
    
    @Test
    @DisplayName("awohara: general test to make sure geographic coordinate values can be properly set.")
    public void testGeographicCoordinate()
    {
        GeographicCoordinate newCoordinate = new GeographicCoordinate(5,2);

        boolean correctLongitude = newCoordinate.longitude == 5;
        boolean correctLatitude = newCoordinate.latitude == 2;

        assertTrue(correctLongitude && correctLatitude);
    }
}
