package model;

public enum TerrainType {
    GRASS(1.0, true, "Plaine"),
    WATER(0.0, false, "Eau"),
    MOUNTAIN(0.5, true, "Montagne"),
    FOREST(0.8, true, "Forêt");

    private final double movementModifier;
    private final boolean accessible;
    private final String displayName;

    TerrainType(double movementModifier, boolean accessible, String displayName) {
        this.movementModifier = movementModifier;
        this.accessible = accessible;
        this.displayName = displayName;
    }

    public double getMovementModifier() { return movementModifier; }
    public boolean isAccessible() { return accessible; }
    public String getDisplayName() { return displayName; }
}
