package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSqlGuide {
    
    @Test
    @DisplayName("awohara: test the places method, does not return null")
    public void testPlacesNull()
    {
        try {assertNotEquals(null, sqlGuide.SQL_DataBase.places("place", 1L));}
        catch (Exception e) { /* uh oh */}
    }

    @Test
    @DisplayName("awohara: test the getPlaces method, does not return null")
    public void testGetPlacesNull()
    {
        try {assertNotEquals(null, sqlGuide.getPlaces("place", 1L));}
        catch (Exception e) { /* uh oh */}
    }

    @Test
    @DisplayName("awohara: test the getPlaces method, contains at least something")
    public void testGetPlacesContainsSomething()
    {
        try {assertNotEquals(0, (sqlGuide.getPlaces("Galveston", 1L)).size());}
        catch (Exception e) { /* uh oh */}
    }

    @Test
    @DisplayName("awohara: test the found method, returns correct number.")
    public void testFoundCorrectValue()
    {
        try{assertEquals(12, sqlGuide.SQL_DataBase.found("Galveston"));}
        catch(Exception e) {assertTrue(false);}
    }

    @Test
    @DisplayName("awohara: test the get found method, returns same value as found method")
    public void testGetFoundCorrectValue()
    {
        try{assertEquals(sqlGuide.SQL_DataBase.found("Denver"), sqlGuide.getFound("Denver"));}
        catch(Exception e) {assertTrue(false);}
    }

    @Test
    @DisplayName("awohara: test the match method, returns string containing correct values")
    public void testMatchContains()
    {
        String returnVal = sqlGuide.Select.match("place", 1);
        boolean hasLimit = returnVal.contains("LIMIT 1");
        boolean hasDistinct = returnVal.contains("DISTINCT");
        boolean hasMatch = returnVal.contains("place");

        assertTrue(hasLimit && hasDistinct && hasMatch);
    }

    @Test
    @DisplayName("jakober: test for found method, makes sure return string contains correct value")
    public void testFoundContains() 
    {
        String returnVal = sqlGuide.Select.found("place");
        assertTrue(returnVal.contains("place"));
    }

    @Test
    @DisplayName("awohara: test that match string matches with more than just the name, found is 1.  Credit to chloehes.json for test idea.")
    public void testFindMatching()
    {
        assertEquals(1L, sqlGuide.getFound("kgeg"));
    }

    @Test
    @DisplayName("wesjones: test that matches more than 1 string:  Credit to eto.json")
    public void testFindMatching2()
    {
        assertEquals(2L, sqlGuide.getFound("pizza"));
    }
}
