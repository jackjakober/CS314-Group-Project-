package com.tco.misc;

import java.util.HashMap;
import com.tco.requests.Place;

public final class ThreeOpt extends TwoOpt {


    public static class Indices {
        public int i = 0;
        public int j = 0;
        public int k = 0;

        public Indices(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }
    }

    public static Place[] perform3Opt(Place[] places){
        Boolean improvement = false; // change to true once 3opt is complete
        int n = places.length; 
        while(improvement == true){
            improvement = false;
            for(int i = 0; i <= n-3; i++){
                for(int j = i+1; j < n-2; j++){
                    for(int k = j+1; k <= n-1; k++){
                        int reversal =  ThreeOptReversals(places, i, j, k);
                        improvement = perform2OptReversals(places, reversal, new Indices(i,j,k));
                    }
                }
            }
        }
        return places;  //  TODO: 3opt algorithm
    }

    public static boolean perform2OptReversals(Place[] places, int reversal, Indices indices)
    {
        if(ThreeOptReverse_I1J(reversal)){
            TwoOpt.reverse(places, (indices.i+1), indices.j);
        }

        if(ThreeOptReverse_J1K(reversal)){
            TwoOpt.reverse(places, (indices.j+1), indices.k);
        }

        if(ThreeOptReverse_I1K(reversal)){
            TwoOpt.reverse(places, (indices.i+1), indices.k);
        }

        return (reversal > 0);
    }

    public static int ThreeOptReversals(Place[] Places, int x, int y, int z){

        return 0;
    } //temporarily placed as void return type, must confirm return type

    private static boolean ThreeOptReverse_I1J(int reversal){return (reversal & 0b001) > 0;}

    private static boolean ThreeOptReverse_J1K(int reversal){return (reversal & 0b010) > 0;}

    private static boolean ThreeOptReverse_I1K(int reversal){return (reversal & 0b100) > 0;}
    
    private boolean makeImprovement(long[] route){ //double check if this is the right type for route
        return false;
    }

    private void makeNecessaryReversals(){} //temporarily void, must confirm the types for the variables
}
