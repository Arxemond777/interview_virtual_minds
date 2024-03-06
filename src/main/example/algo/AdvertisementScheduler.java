package main.example.algo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Advertisement {
    int duration;
    int cost;

    public Advertisement(int duration, int cost) {
        this.duration = duration;
        this.cost = cost;
    }
}

public class AdvertisementScheduler {
//    public static List<Advertisement> findOptimalSchedule(int N, int K, List<Advertisement> advertisements) {
//        advertisements.sort((ad1, ad2) -> ad2.cost - ad1.cost);
//        List<Advertisement> schedule = new ArrayList<>();
//        int totalTime = N;
//        for (Advertisement ad : advertisements) {
//            if (totalTime - ad.duration >= -K && totalTime - ad.duration <= K) {
//                schedule.add(ad);
//                totalTime -= ad.duration;
//            }
//        }
//        return schedule;
//    }

    public static List<Advertisement> findOptimalSchedule(int N, List<Advertisement> advertisements) {
        int[][] dp = new int[advertisements.size() + 1][N + 1];

        for (int i = 1; i <= advertisements.size(); i++) {
            Advertisement ad = advertisements.get(i - 1);
            for (int j = 1; j <= N; j++) {
                if (ad.duration <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - ad.duration] + ad.cost);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        List<Advertisement> schedule = new ArrayList<>();
        int remainingTime = N;
        for (int i = advertisements.size(); i > 0 && remainingTime > 0; i--) {
            if (dp[i][remainingTime] != dp[i - 1][remainingTime]) {
                Advertisement ad = advertisements.get(i - 1);
                schedule.add(ad);
                remainingTime -= ad.duration;
            }
        }

        return schedule;
    }

    public static void main(String[] args) {
        /**
         * {3(1kk)-100,6(1kk)-11,9(1kkk / )-22}
         *
         * 100, y=2
         *
         * [3,6,9,3]
         *
         * 1kk -/+ 999_998-1_000_002
         */
        //momorization, optimization, greedy, parralel
        // bin packing, genetical algo

        // pre-cache

        // watch aproximation algo OR convert into LP
        // starvation

        /**
         * reinforcement learning
         * collaborative filtering or content-based filtering could
         *
         * multi-armed bandit algorithms could be employed to continuously experiment
         * with different ad placements and optimize performance over time.
         */
        List<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement(3, 10));
        advertisements.add(new Advertisement(6, 15));
        advertisements.add(new Advertisement(9, 20));

        int N = 12; // Заданное общее время
        int K = 2; // Разрешенное отклонение от общего времени

        List<Advertisement> optimalSchedule = findOptimalSchedule(N, K, advertisements);
        for (Advertisement ad : optimalSchedule) {
            System.out.println("Duration: " + ad.duration + ", Cost: " + ad.cost);
        }
    }

    public static List<Advertisement> findOptimalSchedule(int N, int K, List<Advertisement> advertisements) {
        // Initialize a 2D array to store the maximum cost for each duration and deviation from N
        int[][] dp = new int[advertisements.size() + 1][2 * K + 1];

        // Iterate through each advertisement
        for (int i = 1; i <= advertisements.size(); i++) {
            Advertisement ad = advertisements.get(i - 1);
            // Iterate through each deviation from N
            for (int j = -K; j <= K; j++) {
                // Iterate through each possible remaining time
                for (int k = 0; k <= N; k++) {
                    // Check if the current advertisement can fit within the remaining time considering the deviation
                    if (ad.duration <= k + j && k + j <= N + K) {
                        // Update the maximum cost for the current duration and deviation
                        dp[i][j + K] = Math.max(dp[i][j + K], dp[i - 1][j + K - ad.duration] + ad.cost);
                    }
                }
            }
        }

        // Create a list to store the optimal schedule
        List<Advertisement> schedule = new ArrayList<>();
        int remainingTime = N;
        int maxCost = dp[advertisements.size()][K];

        // Find the remaining time after considering the deviation
        for (int i = 0; i <= 2 * K; i++) {
            if (dp[advertisements.size()][i] == maxCost) {
                remainingTime = N - (i - K);
                break;
            }
        }

        // Backtrack to find the optimal schedule
        for (int i = advertisements.size(); i > 0 && remainingTime > 0; i--) {
            for (int j = -K; j <= K; j++) {
                if (remainingTime + j >= 0 && remainingTime + j <= N && dp[i][j + K] == maxCost) {
                    Advertisement ad = advertisements.get(i - 1);
                    schedule.add(ad);
                    remainingTime -= (ad.duration + j);
                    break;
                }
            }
        }

        return schedule;
    }

    public int maximizeProfit(int N, int K, int[] durations, int[] profits, int Y) {
        int numAds = durations.length;
        int[][] dp = new int[numAds + 1][N + 1];

        for (int i = 1; i <= numAds; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = dp[i - 1][j]; // Initialize with the profit obtained from previous advertisements
                for (int k = 1; k * durations[i - 1] <= j && k <= Y; k++) { // Consider repeating the same advertisement up to Y times
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * durations[i - 1]] + k * profits[i - 1]);
                }
            }
        }

        return dp[numAds][N];
    }
}
