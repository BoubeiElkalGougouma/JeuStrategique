package model;

public class Soldier extends Unit {
    public Soldier() {
        super("Soldat", 100, 15, 10, 1, 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 50;
            case FOOD -> 10;
            default -> 0;
        };
    }
}
