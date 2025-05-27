package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRandomCoordinates {

    RandomCoordinates randomized = new RandomCoordinates();
    double longitude = randomized.generateValue(true);
    double latitude = randomized.generateValue(false);
    
    @Test
    @DisplayName("ddilsuk: Ensure the range for longitude is within 180 to -180")
    public void testLongitudeRange(){
        assertTrue(longitude > -180.0 && longitude < 180.0);
    }

    @Test
    @DisplayName("ddilsuk: Ensure the range for lattitude is within 90 to -90")
    public void testLatitudeRange(){
        assertTrue(latitude > -90.0 && latitude < 90.0);
    }
}
