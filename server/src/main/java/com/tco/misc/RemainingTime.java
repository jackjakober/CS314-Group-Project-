package com.tco.misc;

public final class RemainingTime {
    
    private static Double timeBegan;
    public static Double responseTimeLimit;

    public static void setTimeBeganToNow()
    {
        timeBegan = (double)System.currentTimeMillis();
    }

    public static double getMilliseconds()
    {
        double now = System.currentTimeMillis();
        double lapse = now - (double)((timeBegan != null) ? timeBegan : 0);
        double val = (responseTimeLimit * 1000) - lapse;
        return Math.max(0,val);
    }
}
