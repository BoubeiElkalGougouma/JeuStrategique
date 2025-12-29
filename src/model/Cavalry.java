package model;

public class Cavalry extends Unit {
    public Cavalry() {
        super("Cavalier", 120, 25, 8, 1, 4);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 100;
            case FOOD -> 20;
            default -> 0;
        };
    }
}
