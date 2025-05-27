package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDistancesRequest {

    private DistancesRequest conf;

    @BeforeEach
    public void createDistancesConfigurationForTestCases() {
        conf = new DistancesRequest();
        conf.buildResponse();
    }

    @Test
    @DisplayName("awohara: Request type is \"distances\"")
    public void testType() {
        String type = conf.getRequestType();
        assertEquals("distances", type);
    }

    @Test
    @DisplayName("awohara: distances is initialized.")
    public void testDistances() {
        assertNotEquals(null, conf.getDistances());
    }

    @Test
    @DisplayName("awohara: test earth radius of type Double")
    public void testEarthRadiusType()
    {
        assertEquals("Double", conf.getEarthRadius().getClass().getSimpleName());
    }

    @Test
    @DisplayName("awohara: distances contains nothing.")
    public void testDistancesWithNoPlaces() {
        int distanceCount = conf.getDistances().size();
        assertEquals(0, distanceCount);
    }

    @Test
    @DisplayName("awohara: distances contains nothing.")
    public void testDistancesWithOnePlace() {

        addPlace("place1","20","-30");

        assertEquals(1, conf.getDistances().size());
    }

    @Test
    @DisplayName("awohara: distances contains 2 distances.")
    public void testDistancesWithTwoPlaces() {

        addPlace("place1","20","-30");
        addPlace("place2","30","-20");

        assertEquals(2, conf.getDistances().size());
    }

    @Test
    @DisplayName("awohara: distances contains 3 distances.")
    public void testDistancesWithThreePlaces() {

        addPlace("place1","20","-30");
        addPlace("place2","30","-20");
        addPlace("place3","35","-20");

        assertEquals(3, conf.getDistances().size());
    }

    @Test
    @DisplayName("awohara: distances contains the correct values.")
    public void testFourPlacesCorrectDistanceValues() {

        addPlace("place1","0.0","0.0");
        addPlace("place2","37.56","126.0");
        addPlace("place3","65.07","-153.98");
        addPlace("place4","1.49","27.16");

        ArrayList<Long> trueDistances = new ArrayList<Long>();
        trueDistances.add(9L);
        trueDistances.add(4L);
        trueDistances.add(9L);
        trueDistances.add(2L);

        assertEquals(trueDistances, conf.getDistances());
    }

    private void addPlace(String name, String latitude, String longitude)
    {
        if(conf.placesHasNotBeenInitialized())
            conf.initializePlaces();

        Place newPlace = new Place();
        newPlace.put("name",name);
        newPlace.put("latitude", latitude);
        newPlace.put("longitude", longitude);

        conf.addPlace(newPlace);

        conf.recalculateDistances();
    }
}