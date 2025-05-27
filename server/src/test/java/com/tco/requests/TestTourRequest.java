package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.tco.misc.OptimizeTour;

public class TestTourRequest {
    private TourRequest conf;

    @BeforeEach
    public void createTourConfigurationForTestCases(){
        conf = new TourRequest();
        conf.buildResponse();
    }

    @Test
    @DisplayName("andrewborchert: Test that request type is \"tour\"")
    public void testType() {
        String type = conf.getRequestType();
        assertEquals("tour", type);
    }
    
    @Test
    @DisplayName("awohara: Test that the resulting trip is not longer than the original")
    public void testShortnessOfTrip() {
        TourRequest tempConf = new TourRequest();
        long originalDistance = tempConf.getTripLength();
        tempConf.buildResponse();
        assertTrue(originalDistance >= tempConf.getTripLength());
    }

    //TODO: Add More Tests when Tour gets implemented
}
