package com.tco.misc;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.requests.Places;

public final class GetDistances {

    public static List<Long> calculateDistances(Places places, Double earthRadius){
        List<Long> distances = new ArrayList<Long>();
        if(thereAreEnoughPlaces(places))
        {
            for(int i = 0; i < places.size(); i++) 
            {
                distances.add((long)calculateDistance(i, new Properties(places, earthRadius)));
            }
        }
        return distances;
    }
    
    private static class Properties
    {
        Places places;
        Double earthRadius;

        public Properties(Places places, Double earthRadius)
        {
            this.places = places;
            this.earthRadius = earthRadius;
        }
    }
    
    private static boolean thereAreEnoughPlaces(Places places){
        return (places.size() >= 1);
    }
    
    public static double calculateDistance(int i, Properties props){

        Places places = props.places;
        Double earthRadius = props.earthRadius;

        int nextIndex = (i+1) % places.size();

        HashMap<String, String> curPlace = places.get(i);
        HashMap<String, String> nextPlace = places.get(nextIndex);

        GeographicCoordinate coordinate1 = 
            new GeographicCoordinate(Double.parseDouble(curPlace.get("longitude")), Double.parseDouble(curPlace.get("latitude")));
            
        GeographicCoordinate coordinate2 = 
            new GeographicCoordinate(Double.parseDouble(nextPlace.get("longitude")), Double.parseDouble(nextPlace.get("latitude")));

        return GreatCircleDistance.calculate(coordinate1, coordinate2, earthRadius);
    }
    
    
    // FOR TESTING PURPOSES

    public static boolean testProperties(Places places, Double earthRadius)
    {
        Properties props = getProperty(places,earthRadius);
        return (props.places == places && props.earthRadius == earthRadius);
    }
    
    public static Properties getProperty(Places places, Double earthRadius)
    {
        return new Properties(places, earthRadius);
    }


}