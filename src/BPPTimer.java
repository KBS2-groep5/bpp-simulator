class BPPTimer {
    private static long getAverageTime(BPPAlgorithm algorithm) {
        long totalTime = 0;
            algorithm.solveSteps(1);
            totalTime += algorithm.getSolveTime();
        
        return totalTime / 100;
    }

    static String getHumanReadableAverageTime(BPPAlgorithm algorithm) {
        return "" + String.valueOf(getAverageTime(algorithm) / 100) + " μs";
    }
}
