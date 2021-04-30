/*
 * Name: Eralp Orkun
 * EID: eao789
 */

import java.util.ArrayList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "responseTime" field set to the optimal response time
     */
    @Override
    public TownPlan findOptimalResponseTime(TownPlan town) {
        /* TODO implement this function */

        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> x = town.getHousePositions();

        System.out.println(n);
        System.out.println(k);
        int[][] r = new int[n+1][k+1];

        for(int n_i = 0; n_i <= n; ++n_i){ //no stations, response time maximum
            r[n_i][0] = Integer.MAX_VALUE;
        }

        for(int k_i = 1; k_i <= k; ++k_i){ //stations == houses, response time of 0
            r[k_i][k_i] = 0;
        }

        for(int n_i = 2; n_i <= n; n_i++){ //if 1 station, response time is (Xmax-Xmin)/2
            r[n_i][1] = (x.get(n_i-1) - x.get(0))/2;
        }

        for(int k_i = 2; k_i <= k; k_i++){
            for (int n_i = (k_i + 1); n_i <= n; n_i++){
                int min = Integer.MAX_VALUE;
                for(int i = 0; i < n_i; i++){
                    int left = r[n_i-(i+1)][k_i-1];
                    int right = (x.get(n_i-1) - x.get((n_i-i)-1))/2;
                    int max;
                    if(left>right){
                        max = left;
                    }
                    else{
                        max = right;
                    }
                    if(max < min){
                        min = max;
                    }
                }
                r[n_i][k_i] = min;
            }
        }
        town.setResponseTime(r[n][k]);
        return town;
    }

    /**
     * Determines the solution of the set of police station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "policeStationPositions" field set to the optimal police station positions
     */
    @Override
    public TownPlan findOptimalPoliceStationPositions(TownPlan town) {
        /* TODO implement this function */




        return town;
    }
}
