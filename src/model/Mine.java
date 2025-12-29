package model;

import java.util.HashMap;
import java.util.Map;

public class Mine extends Building {
    public Mine() {
        super("Mine d'Or", 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 80;
            case WOOD -> 40;
            case STONE -> 20;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        Map<ResourceType, Integer> production = new HashMap<>();
        production.put(ResourceType.GOLD, 15);
        production.put(ResourceType.STONE, 5);
        return production;
    }
}
