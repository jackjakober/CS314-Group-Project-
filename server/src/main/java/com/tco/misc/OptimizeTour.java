package com.tco.misc;

import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.*;

import com.tco.requests.Place;
import com.tco.requests.Places;
import com.tco.misc.GreatCircleDistance;
import com.tco.misc.GeographicCoordinate;
import com.tco.misc.GetDistances;
import com.tco.misc.RemainingTime;

public final class OptimizeTour {

    public static Places nearestNeighbor(Places places, double response, double earthradius) {
        if(places.size() == 0) {
            return places;
        }

        Place startPlace = places.get(0);

        Places bestTour = places;
        Long bestTotalDistance = Long.MAX_VALUE;

        double timeLimit = getTimeLimit(response);
        int startingIndex = 0;
        while(RemainingTime.getMilliseconds() > timeLimit && startingIndex < places.size()) {
            Places placesCopy = new Places(places);
            Places tour = CreateNearestNeighborTour(placesCopy, placesCopy.get(startingIndex), earthradius);
            Long tempTotalDistance = getTotalDistance(tour, earthradius);
            if(tempTotalDistance < bestTotalDistance) {
                bestTotalDistance = tempTotalDistance;
                bestTour = tour;
            }
            startingIndex++;
        }
        bestTour = rotate(bestTour, startPlace);
        return bestTour;
    }

    public static Places CreateNearestNeighborTour(Places places, Place startingPlace, double earthradius) {
        Places tour = new Places();
        tour.add(startingPlace);
        places.remove(startingPlace);
        int tourIndex = 0;
        while(!places.isEmpty()) {
            Place place2Add = findShortestDistance(places, tour.get(tourIndex), earthradius);
            places.remove(place2Add);
            tour.add(place2Add);
            tourIndex++;
        }
        return tour;

    }

    public static Place findShortestDistance(Places places, Place startingPlace, double earthradius) {
        Long bestDistance = Long.MAX_VALUE;
        Place bestPlace = places.get(0);

        for(Place place : places) {
            Long tempDistance = calculateSingleDistance(startingPlace, place, earthradius); 
            if(tempDistance <= bestDistance) {
                bestDistance = tempDistance;
                bestPlace = place;
            }
        }
        return bestPlace;
    }

    public static Long calculateSingleDistance(Place place1, Place place2, double earthradius) {
        GeographicCoordinate coordinate1 = 
                new GeographicCoordinate(Double.parseDouble(place1.get("longitude")), Double.parseDouble(place1.get("latitude")));
            
        GeographicCoordinate coordinate2 = 
                new GeographicCoordinate(Double.parseDouble(place2.get("longitude")), Double.parseDouble(place2.get("latitude")));

        return GreatCircleDistance.calculate(coordinate1, coordinate2, earthradius);
    }

    public static Long getTotalDistance(Places places, double earthradius) {
        Long totalDistance = 0L;
        List<Long> distances = GetDistances.calculateDistances(places, earthradius);
        for(Long distance : distances) {
            totalDistance += distance;
        }
        return totalDistance;
    }

    public static double getTimeLimit(double response) {
        double arbitrary = 0.30;
        return arbitrary * (response * 1000);
    }
    
    public static int NumStartingCities(int numPlaces, double response) {
        int arbitrary = 10;
        if(numPlaces > (arbitrary * response))
             return (int)(arbitrary * response);
        return numPlaces;
    }

    public static Places rotate(Places places, Place startPlace) {
        Collections.rotate(places, -places.indexOf(startPlace));
        return places;
    }
}
