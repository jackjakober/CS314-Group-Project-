package com.tco.misc;

import com.tco.requests.Places;
import com.tco.requests.Place;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGetDistances {

    @Test
    @DisplayName("awohara: properties are set")
    public void testProperties()
    {
        Places places = new Places();
        places.add(newPlace("place","0.0","0.0"));

        assertTrue(GetDistances.testProperties(places, 200.0));
    }

    @Test
    @DisplayName("awohara: calculateDistance returns correct value")
    public void testCalculateDistance()
    {
        Places places = new Places();
        places.add(newPlace("place1","37.56","126.0"));
        places.add(newPlace("place2","65.07","-153.98"));

        assertEquals(4, GetDistances.calculateDistance(0,GetDistances.getProperty(places,4.5)));
    }

    @Test
    @DisplayName("jakober: method returns correct distances values")
    public void testFourPlacesCorrectDistanceValues() {

        Places places = new Places();

        places.add(newPlace("place1","0.0","0.0"));
        places.add(newPlace("place2","37.56","126.0"));
        places.add(newPlace("place3","65.07","-153.98"));
        places.add(newPlace("place4","1.49","27.16"));

        ArrayList<Long> trueDistances = new ArrayList<Long>();
        trueDistances.add(9L);
        trueDistances.add(4L);
        trueDistances.add(9L);
        trueDistances.add(2L);

        assertEquals(trueDistances, GetDistances.calculateDistances(places,4.5));
    }


    private Place newPlace(String name, String latitude, String longitude)
    {
        Place newPlace = new Place();
        newPlace.put("name",name);
        newPlace.put("latitude", latitude);
        newPlace.put("longitude", longitude);

        return newPlace;
    }

}