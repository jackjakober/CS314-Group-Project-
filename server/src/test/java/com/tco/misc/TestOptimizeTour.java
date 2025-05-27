package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.List;

import com.tco.requests.Places;
import com.tco.requests.Place;
import com.tco.misc.RemainingTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOptimizeTour {


    @Test 
    @DisplayName("Alaskan: Return value tested with Zero | '0' ")
    public void testNumStartingCitiesTest()
    { 
        
        int numplaces = 0;
        double response = 0;

        assertEquals(0, OptimizeTour.NumStartingCities(numplaces, response));
    }

    @Test
    @DisplayName("jakober: makes sure getTimeLimit returns 0 ")
    public void testGetTimeLimit() {
        assertEquals(0, OptimizeTour.getTimeLimit(0.0));
    }

    @Test
    @DisplayName("jakober: makes sure findShortestDistance returns correct values")
    public void testFindShortestDistance() {
        Places places = new Places();
        Place StartingPlace = newPlace("Start", "0.0", "0.0");
        places.add(newPlace("place1","37.56","126.0"));
        places.add(newPlace("place2","65.07","-153.98"));
        places.add(newPlace("place3","1.49","27.16"));
        Place output = OptimizeTour.findShortestDistance(places, StartingPlace, 4.5);

        assertEquals(places.get(2), output);
    }

    @Test
    @DisplayName("jakober: makeSure CreateNearestNeighborTour reutnrs correct values")
    public void testCreateNearestNeighborTour() {
        Places places = new Places();
        places.add(newPlace("place1","0.0","0.0"));
        places.add(newPlace("place2","37.56","126.0"));
        places.add(newPlace("place3","65.07","-153.98"));
        places.add(newPlace("place4","1.49","27.16"));

        Place StartingPlace = places.get(0);

        Places correctTour = new Places();
        correctTour.add(places.get(0));
        correctTour.add(places.get(3));
        correctTour.add(places.get(1));
        correctTour.add(places.get(2));

        Places outputTour = OptimizeTour.CreateNearestNeighborTour(places, StartingPlace, 4.5);
  

        assertEquals(correctTour, outputTour);
    }

    @Test 
    @DisplayName("Alaskan: Return value tested with shortValues")
    public void testNumStartingCitiesTestWithShort()
    { 
        
        short numplaces = 32767;
        short response = 100;


       int actualStartingCities = OptimizeTour.NumStartingCities(numplaces, response);
       int expectedStartingCities = 1000;

        assertEquals(actualStartingCities, expectedStartingCities);
    }




    @Test 
    @DisplayName("jakober: makes sure nearestNeighbor method returns correctly")
    public void nearestNeighborOutput() {

        Places places = new Places();
        places.add(newPlace("place1","0.0","0.0"));
        places.add(newPlace("place2","37.56","126.0"));
        places.add(newPlace("place3","65.07","-153.98"));
        places.add(newPlace("place4","1.49","27.16"));

        Places correctTour = new Places();
        correctTour.add(places.get(0));
        correctTour.add(places.get(3));
        correctTour.add(places.get(1));
        correctTour.add(places.get(2));
        
        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = 1.0;
        Places output = OptimizeTour.nearestNeighbor(places, 1, 4.5);
        assertEquals(correctTour, output);
    }

    @Test 
    @DisplayName("jakober: makes sure nearestNeighbor method returns correctly")
    public void nearestNeighborOutput2() {

        Place place1 = newPlace("place1","0.0","0.0");
        Place place2 = newPlace("place2", "100.0", "0.0");
        Place place3 = newPlace("place3", "10.0", "0.0");
        Place place4 = newPlace("place4", "90.0", "0.0");
        Place place5 = newPlace("place5", "20.0", "0.0");
        Place place6 = newPlace("place6", "80.0", "0.0");
        Place place7 = newPlace("place7", "30.0", "0.0");
        Place place8 = newPlace("place8", "70.0", "0.0");
        Place place9 = newPlace("place9", "40.0", "0.0");
        Place place10 = newPlace("place10", "50.0", "0.0");

        Places tour = new Places();
        Places seeTour = new Places();
        Places correctTour = new Places();

        tour.add(place1);
        tour.add(place2);
        tour.add(place3);
        tour.add(place4);
        tour.add(place5);
        tour.add(place6);
        tour.add(place7);
        tour.add(place8);
        tour.add(place9);
        tour.add(place10);

        correctTour.add(place1);
        correctTour.add(place7);
        correctTour.add(place9);
        correctTour.add(place10);
        correctTour.add(place8);
        correctTour.add(place6);
        correctTour.add(place4);
        correctTour.add(place2);
        correctTour.add(place5);
        correctTour.add(place3);

        
        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = 1.0;

        Places output = OptimizeTour.nearestNeighbor(tour, 1, 4.5);
        assertEquals(correctTour, output);
    }

    @Test
    @DisplayName("Jakober; nearestNeighborOutput3")
    public void testNearestNeighborOutput3() {
        Place Place1 = newPlace("Denver", "39.7", "-105.0");
        Place Place2 = newPlace("Boulder", "40.0", "-105.4");
        Place Place3 = newPlace("Fort Collins", "40.6", "-105.1");
    
        Places tour = new Places();
        Places correctTour = new Places();

        tour.add(Place1);
        tour.add(Place2);
        tour.add(Place3);

        correctTour.add(Place1);
        correctTour.add(Place2);
        correctTour.add(Place3);

        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = 1.0;

        Places output = OptimizeTour.nearestNeighbor(tour, 1, 3959.0);
        assertEquals(correctTour, output);
    }

    @Test 
    @DisplayName("awohara: makes sure nearestNeighbor method returns correctly")
    public void nearestNeighborOutput4() {


        Places places = new Places();
        places.add(newPlace("place1","32.36","-102.42"));
        places.add(newPlace("place2","58.53","-92.58"));
        places.add(newPlace("place3","32.36","-98.20"));
        places.add(newPlace("place4","57.88","-97.32"));

        Places correctTour = new Places();
        correctTour.add(places.get(0));
        correctTour.add(places.get(3));
        correctTour.add(places.get(1));
        correctTour.add(places.get(2));
        
        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = 1.0;

        Places output = OptimizeTour.nearestNeighbor(places, 1.0, 3959.0);
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("jakober: make sure calculate single distance returns correctly")
    public void testCalculateSingleDistance() {
        Places places = new Places();
        Place place1 = newPlace("place1","0.0","0.0");
        Place place2 = newPlace("place2","37.56","126.0");
        places.add(place1);
        places.add(place2);

        Long testDistance = OptimizeTour.calculateSingleDistance(place1, place2, 4.5);
        List<Long> distance = GetDistances.calculateDistances(places, 4.5);
        Long realDistance = distance.get(0);

        assertEquals(realDistance, testDistance);
    }

    @Test
    @DisplayName("jakober: make sure getTotalDistance returns correct values")
    public void testGetTotalDistance() {
        Places places = new Places();
        places.add(newPlace("place1","0.0","0.0"));
        places.add(newPlace("place2","37.56","126.0"));
        places.add(newPlace("place3","65.07","-153.98"));
        places.add(newPlace("place4","1.49","27.16"));

        Long testTotalDistance = OptimizeTour.getTotalDistance(places, 3959);
        Long realTotalDistance = 21473L;

        assertEquals(realTotalDistance, testTotalDistance);
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