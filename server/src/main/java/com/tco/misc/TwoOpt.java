package com.tco.misc;

import java.util.HashMap;
import com.tco.requests.Place;

import com.tco.misc.GreatCircleDistance;
import com.tco.misc.GeographicCoordinate;


public class TwoOpt {


    
    public static Place[] perform2Opt(Place[] places, double earthRadius)
    {
        boolean improvements = true;
        while(improvements) {
            improvements = makeImprovements(places, earthRadius);
        }

        return places;
    }

    public static boolean makeImprovements(Place[] places, double earthRadius) {
        int n = places.length;
        boolean improvementMade = false;

        for (int i = 0; i <= n - 3; i++) {
            for(int k = i + 2; k <= n-1; k++) {
                if(improves(places,i,k,earthRadius)) {
                    reverse(places,i+1,k);
                    improvementMade = true;
                }
            }
        }

        return improvementMade;
    }

    public static boolean improves(Place[] places, int i, int k, double earthRadius)
    {
        double newLegDistance = legdis(places,i,k,earthRadius) + legdis(places,i+1,k+1, earthRadius);
        double curLegDistance = legdis(places,i,i+1,earthRadius) + legdis(places,k,k+1, earthRadius);

        return newLegDistance < curLegDistance;
    }

    private static double legdis(Place[] places, int index1, int index2, double earthRadius)
    {
        return GreatCircleDistance.calculate(
            getCoordinate(places[index1 % places.length], earthRadius), 
            getCoordinate(places[index2 % places.length], earthRadius),
            earthRadius);
    }

    private static GeographicCoordinate getCoordinate(Place place, double earthRadius)
    {
        return new GeographicCoordinate(Double.parseDouble(place.get("latitude")), Double.parseDouble(place.get("longitude")));
    }

    public static void reverse(Place[] places, int i1, int k)
    {
        while(i1 < k)
        {
            Place temp = places[i1];
            places[i1] = places[k];
            places[k] = temp;
            i1++;
            k--;
        }
    }
}
