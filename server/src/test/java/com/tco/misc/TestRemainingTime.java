package com.tco.misc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.List;

import java.lang.Thread;

import com.tco.requests.Places;
import com.tco.requests.Place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRemainingTime {
    
    @Test
    @DisplayName("awohara: method returns something that isn't 0.")
    public void testGetMilliseconds()
    {
        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = 1.0;
        try {Thread.sleep(500);} catch (Exception e) {}
        double val = RemainingTime.getMilliseconds();
        assertTrue(val <= 500 && val >= 0);
    }


    @Test
    @DisplayName("alaskan: ")
    public void testNegativeValueMilliSeconds()
    {
        double squeeze = 0.0; 
        
        
        RemainingTime.setTimeBeganToNow();
        RemainingTime.responseTimeLimit = -1.0;
        
        
        try {
            Thread.sleep(105);
            squeeze = RemainingTime.getMilliseconds();
        }
        catch (Exception e) {}

       
        assertTrue(squeeze >= 0);
        
       
                  
    }

}
