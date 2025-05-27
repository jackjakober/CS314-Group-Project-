package com.tco.misc;

import com.tco.requests.Places;
import com.tco.requests.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestThreeOpt {
    
    @Test
    @DisplayName("awohara: reversal equals 0, nothing happens to places.")
    public void testperform2OptReversalsZero()
    {
        Place[] placesOriginal = new Place[]{
            newPlace("place1","1","2"), 
            newPlace("place2","2","3"), 
            newPlace("place3","4","5"), 
            newPlace("place4","5","6")};

        Place[] placesNew = new Place[]{
            newPlace("place1","1","2"), 
            newPlace("place2","2","3"), 
            newPlace("place3","4","5"), 
            newPlace("place4","5","6")};

        ThreeOpt.perform2OptReversals(placesNew, 0, new ThreeOpt.Indices(0,0,0));

        for(int i = 0; i < placesOriginal.length; i++) {
            assertEquals(placesOriginal[i], placesNew[i]);
        }
    }

    private Place newPlace(String name, String latitude, String longitude)
    {
        Place newPlace = new Place();
        newPlace.put("name",name);
        newPlace.put("latitude", latitude);
        newPlace.put("longitude", longitude);

        return newPlace;
    }

    @Test
    @DisplayName("awohara: 3opt method improves or at least does not make worse the trip.")
    public void test3optWorks() {

        double earthRadius = 3000;

        Place[] places = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        Place[] newPlaces = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        newPlaces = ThreeOpt.perform3Opt(newPlaces);

        assertTrue(newPlaces != places);

        double lengthOne = 0; double lengthTwo = 0;

        List<Long> distances1 = GetDistances.calculateDistances(new Places(Arrays.asList(places)), earthRadius);
        List<Long> distances2 = distances1;
        if(newPlaces.length != 0)
            distances2 = GetDistances.calculateDistances(new Places(Arrays.asList(newPlaces)), earthRadius);

        for(int i = 0; i < distances1.size(); i++) {
            lengthOne += distances1.get(i); lengthTwo += distances2.get(i);
        }

        assertTrue(lengthOne >= lengthTwo);
    }
}
