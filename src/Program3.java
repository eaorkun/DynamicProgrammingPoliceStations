/*
 * Name: Eralp Orkun
 * EID: eao789
 */

import java.util.ArrayList;

/**
 * Your solution goes in this class.
 * <p>
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * <p>
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
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> x = town.getHousePositions();
        int[][] r = new int[n + 1][k + 1];

        for (int n_i = 0; n_i <= n; ++n_i) { //no stations, response time maximum
            r[n_i][0] = Integer.MAX_VALUE;
        }

        for (int k_i = 1; k_i <= k; ++k_i) { //stations == houses, response time of 0
            r[k_i][k_i] = 0;
        }

        for (int n_i = 2; n_i <= n; n_i++) { //if 1 station, response time is (Xmax-Xmin)/2
            r[n_i][1] = (x.get(n_i - 1) - x.get(0)) / 2;
        }

        for (int k_i = 2; k_i <= k; k_i++) {
            for (int n_i = (k_i + 1); n_i <= n; n_i++) {
                int r_min_choices = Integer.MAX_VALUE; // Will be overwritten, holds minimum response time for all choices of specific house from far edge
                for (int i = 0; i < n_i; i++) {
                    int r_subproblem = r[n_i - (i + 1)][k_i - 1]; //remaining subproblem
                    int r_choice = (x.get(n_i - 1) - x.get((n_i - i) - 1)) / 2; //current "choice" of house from far right edge
                    int max;
                    if (r_subproblem > r_choice) { //gets max of r_subproblem and r_choice
                        max = r_subproblem;
                    }
                    else {
                        max = r_choice;
                    }
                    if (max < r_min_choices) { //updates the min if less than current min
                        r_min_choices = max;
                    }
                }
                r[n_i][k_i] = r_min_choices; //fills in table with minimum response time
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
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> x = town.getHousePositions();
        int[][] r = new int[n + 1][k + 1];
        PolicePositions[][] c = new PolicePositions[n + 1][k + 1];

        for (int n_i = 0; n_i <= n; ++n_i) { //no stations, response time maximum
            r[n_i][0] = Integer.MAX_VALUE;
        }

        for (int k_i = 1; k_i <= k; ++k_i) { //stations == houses, response time of 0
            r[k_i][k_i] = 0;
            c[k_i][k_i] = new PolicePositions(x.get(k_i - 1), (k_i - 1));
        }

        for (int n_i = 2; n_i <= n; n_i++) { //if 1 station, response time is (Xmax-Xmin)/2
            r[n_i][1] = (x.get(n_i - 1) - x.get(0)) / 2;
            c[n_i][1] = new PolicePositions(r[n_i][1] + x.get(0), 0);
        }

        for (int k_i = 2; k_i <= k; k_i++) {
            for (int n_i = (k_i + 1); n_i <= n; n_i++) {
                int r_min_choices = Integer.MAX_VALUE; // Will be overwritten, holds minimum response time for all choices of specific house from far edge
                PolicePositions c_min_choices = new PolicePositions(0, 0);
                for (int i = 0; i < n_i; i++) {
                    int r_subproblem = r[n_i - (i + 1)][k_i - 1]; //remaining subproblem
                    int r_choice = (x.get(n_i - 1) - x.get((n_i - i) - 1)) / 2; //current "choice" of house from far right edge
                    int max;
                    if (r_subproblem > r_choice) { //gets max of r_subproblem and r_choice
                        max = r_subproblem;
                    }
                    else {
                        max = r_choice;
                    }
                    if (max < r_min_choices) { //updates the min if less than current min
                        r_min_choices = max;
                        c_min_choices.setPosition(r_choice + x.get((n_i - i) - 1));
                        c_min_choices.setN_subproblem((n_i - (i + 1)));
                    }
                }
                r[n_i][k_i] = r_min_choices; //fills in table with minimum response time
                c[n_i][k_i] = c_min_choices; //fills in table with position of latest choice (plus backwards indices)
            }
        }

        ArrayList<Integer> reversePositions = new ArrayList<>();
        int n_sub = n;
        for (int k_i = k; k_i > 0; k_i--) { //follow path backwards from end, add to list O(k)
            PolicePositions position = c[n_sub][k_i];
            n_sub = position.getN_subproblem();
            reversePositions.add(position.getPosition());
        }

        ArrayList<Integer> sortedPositions = new ArrayList<>(); //read the list backwards, O(k)
        for (int k_i = k; k_i > 0; k_i--) {
            sortedPositions.add(reversePositions.get(k_i - 1));
        }

        town.setPoliceStationPositions(sortedPositions); //return list
        //System.out.println("Response time of police stations is " + findResponse(town)); //for debugging only
        return town;
    }


    //for debugging purposes (to find response time from police set)
    public int findResponse(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> police = town.getPoliceStationPositions();
        ArrayList<Integer> house = town.getHousePositions();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; ++i) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < k; ++j) {
                int difference = house.get(i) - police.get(j);
                if (difference < 0) {
                    difference = -1 * difference;
                }
                if (difference < min) {
                    min = difference;
                }
            }
            if (min > max) {
                max = min;
            }
        }
        return max;
    }
}
