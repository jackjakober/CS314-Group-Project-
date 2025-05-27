package com.tco.misc;

public class RandomCoordinates {
    
    public double generateValue(boolean isLongitude){
        double coordinateLimit = 90;
        if(isLongitude){
            coordinateLimit = 180;
        }

        double coordinate = Math.random() * coordinateLimit;
        coordinate *= Math.round(Math.random()) * 2 - 1;

        return coordinate;
    }

    public String coordinateStringConverter(double coordinateValue){
        return String.format("%.5f",coordinateValue);
    }

    public String generateCoordinates(){
        String latitude = Double.toString(generateValue(false));
        String longitude = Double.toString(generateValue(true));
        return latitude + " " + longitude;
    }
}
