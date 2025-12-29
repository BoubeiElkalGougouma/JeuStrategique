package model;

import java.util.HashMap;
import java.util.Map;

public class Farm extends Building {
    public Farm() {
        super("Ferme", 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 50;
            case WOOD -> 30;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        Map<ResourceType, Integer> production = new HashMap<>();
        production.put(ResourceType.FOOD, 20);
        return production;
    }
}

