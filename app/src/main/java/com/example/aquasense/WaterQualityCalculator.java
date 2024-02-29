package com.example.aquasense;

public class WaterQualityCalculator {
    private static final double PH_THRESHOLD = 7.0;
    private static final double DO_THRESHOLD = 8.0;
    private static final double TEMPERATURE_THRESHOLD = 25.0;
    private static final double TURBIDITY_THRESHOLD = 5.0;
    private static final double CHLORIDES_THRESHOLD = 50.0;
    private static final int PH_WEIGHT = 25;
    private static final int DO_WEIGHT = 25;
    private static final int TEMPERATURE_WEIGHT = 20;
    private static final int TURBIDITY_WEIGHT = 15;
    private static final int CHLORIDES_WEIGHT = 15;
    public static int calculateWaterQuality(double pH, double dissolvedOxygen, double temperature,
                                            double turbidity, double chlorides) {

        double weightedSum = 0;

        weightedSum += calculateWeightedScore(pH, PH_THRESHOLD, PH_WEIGHT);
        weightedSum += calculateWeightedScore(dissolvedOxygen, DO_THRESHOLD, DO_WEIGHT);
        weightedSum += calculateWeightedScore(temperature, TEMPERATURE_THRESHOLD, TEMPERATURE_WEIGHT);
        weightedSum += calculateWeightedScore(turbidity, TURBIDITY_THRESHOLD, TURBIDITY_WEIGHT);
        weightedSum += calculateWeightedScore(chlorides, CHLORIDES_THRESHOLD, CHLORIDES_WEIGHT);

        int waterQualityScore = (int) ((weightedSum / 100) * 100);

        return Math.min(Math.max(waterQualityScore, 0), 100);
    }
    private static double calculateWeightedScore(double value, double threshold, int weight) {

        if (value >= threshold) {
            return weight;
        }

        double deviation = threshold - value;
        return Math.max(weight * (1 - deviation / threshold), 0);
    }
}