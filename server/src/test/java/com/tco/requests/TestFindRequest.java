package com.tco.requests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.tco.misc.sqlGuide;

public class TestFindRequest {

    private FindRequest conf;

    @BeforeEach
    public void createFindConfigurationForTestCases() {
        conf = new FindRequest();
        conf.buildResponse();
    }

    @Test
    @DisplayName("awohara: test the request type equals \"find\"")
    public void testRequestType()
    {
        assertEquals("find", conf.requestType);
    }

    @Test
    @DisplayName("awohara: test the getPlacesFromDataBase method for correct result. Requires connection to the Database.")
    public void testGetPlacesFromDataBaseCorrectReturn()
    {
        FindRequest myRequest = new FindRequest("Houston", 2L);

        ArrayList<HashMap<String,String>> expectedResult =
            sqlGuide.getPlaces("Houston", 2L);
        ArrayList<HashMap<String,String>> actualResult =
            myRequest.getPlacesFromDatabase();

            assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("awohara: test the match property.")
    public void testRequestMatch()
    {
        assertEquals("", conf.getMatch());
    }

    @Test
    @DisplayName("awohara: test the found property has the correct value")
    public void testRequestFoundProperty()
    {
        FindRequest myRequest = new FindRequest("New York",1L);
        myRequest.buildResponse();
        assertEquals(sqlGuide.getFound("New York"), myRequest.getFound());
    }

    @Test
    @DisplayName("awohara: test the limit property.")
    public void testRequestLimit()
    {
        assertEquals(10L, conf.getLimit());
    }
    
    @Test
    @DisplayName("awohara: test the get places list method, does not return null")
    public void testGetPlacesFromDatabase()
    {
        assertNotEquals(null, conf.getPlacesFromDatabase());
    }

    @Test
    @DisplayName("awohara: test the places property to make sure it is not null")
    public void testPlacesProperty()
    {
        assertNotEquals(null,conf.getPlaces());
    }

    @Test
    @DisplayName("awohara: test the found property to make sure it is 0 at least")
    public void testFoundProperty()
    {
        assertTrue(0L <= conf.getFound());
    }

    @Test
    @DisplayName("awohara: test for special situation of limit 0, where all places found should be returned.")
    public void testPlacesLimit0Specifications()
    {
        assertTrue(testReturnedEqualsFound("London"));
        assertTrue(testReturnedEqualsFound("dave"));
        assertTrue(testReturnedEqualsFound("Houston"));
    }

    public boolean testReturnedEqualsFound(String match)
    {
        FindRequest myRequest = new FindRequest(match,0L);
        myRequest.buildResponse();
        return myRequest.getFound() == myRequest.getPlaces().size();
    }
}