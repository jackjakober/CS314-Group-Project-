package com.tco.misc;

public final class GreatCircleDistance {

  
    public static double toRadians(double degreeValue) {
        return Math.toRadians(degreeValue);
    }

    public static long calculate(GeographicCoordinate coordinate1, 
    GeographicCoordinate coordinate2, double earthRadius)
    {
        double latitude1 = toRadians(coordinate1.latitude);
        double latitude2 = toRadians(coordinate2.latitude);
        double longitude1 = toRadians(coordinate1.longitude);
        double longitude2 = toRadians(coordinate2.longitude);

        double longdiff = Math.abs(longitude2 - longitude1);
        double latdiff = Math.abs(latitude2 - latitude1); 

        double numerator = calculateNumerator(latitude1, latitude2, longdiff);
        double denominator = calculateDenominator(latitude1, latitude2, longdiff);

        double centeral_angle = Math.atan2(numerator,denominator);
        
        return Math.round((centeral_angle * earthRadius));
    }

    private static double calculateDenominator(double lat1, double lat2, double longdiff)
    {
        return (Math.sin(lat1) * Math.sin(lat2)) + (Math.cos(lat1) * Math.cos(lat2) * Math.cos(longdiff));
    }

    private static double calculateNumerator(double lat1, double lat2, double longdiff)
    {
            double inside1 = Math.pow(Math.cos(lat2) * Math.sin(longdiff),2);
            double inside2 = Math.pow(Math.cos(lat1) * Math.sin(lat2) - (Math.sin(lat1) * Math.cos(lat2) * Math.cos(longdiff)),2);

            return Math.sqrt(inside1 + inside2);
    }

}
