package towerRoyal;

public final class Random {

    private static final java.util.Random RANDOM_GENERATOR =
            new java.util.Random(System.nanoTime());

    private Random() {}

    public static double nextDouble() {
        return RANDOM_GENERATOR.nextDouble();
    }

    public static boolean nextBoolean() {
        return RANDOM_GENERATOR.nextBoolean();
    }

    public static int nextInt() {
        return RANDOM_GENERATOR.nextInt();
    }

    public static int nextInt(int bound) {
        return RANDOM_GENERATOR.nextInt(bound);
    }
}