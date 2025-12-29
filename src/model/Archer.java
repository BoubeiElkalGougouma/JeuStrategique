package model;

public class Archer extends Unit {
    public Archer() {
        super("Archer", 70, 20, 5, 3, 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 60;
            case WOOD -> 20;
            case FOOD -> 8;
            default -> 0;
        };
    }
}
