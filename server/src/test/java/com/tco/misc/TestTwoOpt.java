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

public class TestTwoOpt {


    @Test
    @DisplayName("awohara: The whole places array gets reversed")
    public void test2optReverseWholeArray()
    {
        Place[] places = new Place[]{newPlace("place1", "1","2"),newPlace("place2", "1","4"),newPlace("place3", "3","2"),newPlace("place4", "2","2")};
        Place[] placesReversed = new Place[]{newPlace("place4", "2","2"),newPlace("place3", "3","2"),newPlace("place2", "1","4"),newPlace("place1", "1","2")};
        TwoOpt.reverse(places,0,3);

        for(int i = 0; i < 4; i++)
        {
            assertEquals(places[i],placesReversed[i]);
        }
    }    

    @Test
    @DisplayName("awohara: method recognizes the improvement")
    public void test2optImprovesCorrectness()
    {
        Place[] places = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        assertTrue(TwoOpt.improves(places,0,2,3000));
    }

    @Test
    @DisplayName("awohara: method returns true.")
    public void test2optMakeImprovementActions()
    {
        Place[] places = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        assertTrue(TwoOpt.makeImprovements(places,3000));
    }

    @Test
    @DisplayName("awohara: method reorders list of places correctly")
    public void test2optMakeImprovementCorrectness() {
        Place[] places = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        Place[] placesReversed = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07"),newPlace("place2", "42.37","-104.80")};
        TwoOpt.makeImprovements(places,3000);

        for(int i = 0; i < 4; i++) {
            assertEquals(places[i],placesReversed[i]);
        }
    }

    @Test
    @DisplayName("awohara: 2opt method improves or at least does not make worse the trip.")
    public void test2optWorks() {

        double earthRadius = 3000;

        Place[] places = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        Place[] newPlaces = new Place[]{newPlace("place1", "40.19","-106.53"),newPlace("place2", "42.37","-104.80"),newPlace("place3", "39.80","-105.15"),newPlace("place4", "41.93","-103.07")};
        newPlaces = TwoOpt.perform2Opt(newPlaces, earthRadius);

        assertTrue(newPlaces != places);

        double lengthOne = 0; double lengthTwo = 0;

        List<Long> distances1 = GetDistances.calculateDistances(new Places(Arrays.asList(places)), earthRadius);
        List<Long> distances2 = GetDistances.calculateDistances(new Places(Arrays.asList(newPlaces)), earthRadius);

        for(int i = 0; i < distances1.size(); i++) {
            lengthOne += distances1.get(i); lengthTwo += distances2.get(i);
        }

        assertTrue(lengthOne >= lengthTwo);
    }
    
    @Test
    @DisplayName("awohara: places given into perform2Opt are all in the returned list")
    public void testPeform2OptConsistency()
    {
        Place[] places = new Place[]{newPlace("MyPlace1","2","3"), newPlace("MyPlace2","4","1"), newPlace("MyPlace3","6","2")};

        Place[] returned = TwoOpt.perform2Opt(places, 3000);

        for (int i = 0; i < places.length; i++)
        {
            Place curPlace = places[i];

            for (int j = 0; j < returned.length; j++)
            {
                if(returned[j] == places[i])
                {
                    returned = remove(returned, j);
                    j--;
                }
            }
        }

        assertEquals(0,returned.length);
    }

    private Place[] remove(Place[] places, int index)
    {
        Place[] newPlaces = new Place[places.length-1];

        for(int i = 0; i < places.length; i++)
        {
            if(i != index)
            {
                if(i < index)
                    newPlaces[i] = places[i];
                else
                    newPlaces[i-1] = places[i];
            }
        }

        return newPlaces;

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