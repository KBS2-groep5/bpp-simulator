class BPPTimer {
    private static long getAverageTime(BPPAlgorithm algorithm) {
        long totalTime = 0;
        for(int i = 0; i < 100; i++) {
            algorithm.solveSteps(100);
            totalTime += algorithm.getSolveTime();
        }
        return totalTime / 100;
    }

    static String getHumanReadableAverageTime(BPPAlgorithm algorithm) {
        return "" + String.valueOf(getAverageTime(algorithm) / 1000) + " μs";
    }
}
