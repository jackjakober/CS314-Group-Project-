package com.tco.requests;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tco.misc.GreatCircleDistance;
import com.tco.misc.GeographicCoordinate;
import com.tco.misc.GetDistances;

public class DistancesRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(DistancesRequest.class);

    private Double earthRadius;
    private Places places;
    private List<Long> distances;

    @Override
    public void buildResponse() {

        if(placesHasNotBeenInitialized())
            initializePlaces();

        distances = GetDistances.calculateDistances(places,earthRadius);

        log.trace("buildResponse -> {}", this);
    }

    public boolean placesHasNotBeenInitialized()
    {
        return (places == null);
    }

    public void initializePlaces()
    {
        places = new Places();
    }

    /*
     * The following methods exist only for testing purposes and are not used
     * during normal execution, including the constructor.
     */

    public DistancesRequest() {
        this.requestType = "distances";
        this.earthRadius = 4.5;
    }

    public Object getEarthRadius()
    {
        return (Object)(this.earthRadius);
    }

    public void recalculateDistances()
    {
        distances = GetDistances.calculateDistances(places,earthRadius);
    }

    public void addPlace(Place place)
    {
        places.add(place);
    }

    public List<Long> getDistances()
    {
        return this.distances;
    }
}
