package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tco.misc.OptimizeTour;
import com.tco.misc.GetDistances;

import com.tco.misc.RemainingTime;

public class TourRequest extends Request {
    private static final transient Logger log = LoggerFactory.getLogger(TourRequest.class);

    private Double earthRadius;
    private Double response;

    private Places places;

    @Override
    public void buildResponse() {

        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = response;

        places = reorderPlaces(places);

        log.trace("buildResponse -> {}", this);
    }

    public Places reorderPlaces(Places places)
    {
        if(response == 0) return places; 
        
        places = OptimizeTour.nearestNeighbor(places, response, earthRadius);

        return places; // TODO: Use OptimizeTour to return a reordered version of places instead.
    }

    // FOR TESTING PURPOSES

    public TourRequest()
    {
        requestType = "tour";
        places = new Places();
        response = 1.0;
        earthRadius = 3000.0;
    }

    public long getTripLength()
    {
        return OptimizeTour.getTotalDistance(places, earthRadius);
    }
}
