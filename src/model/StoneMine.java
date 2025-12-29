package model;

import java.util.HashMap;
import java.util.Map;

public class StoneMine extends Building {
    public StoneMine() {
        super("Carrière de Pierre", 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 70;
            case WOOD -> 35;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        Map<ResourceType, Integer> production = new HashMap<>();
        production.put(ResourceType.STONE, 15);
        return production;
    }
}
