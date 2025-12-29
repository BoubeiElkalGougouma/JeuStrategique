package model;

import java.util.HashMap;
import java.util.Map;

public class Sawmill extends Building {
    public Sawmill() {
        super("Scierie", 2);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 60;
            case WOOD -> 20;
            case STONE -> 15;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        Map<ResourceType, Integer> production = new HashMap<>();
        production.put(ResourceType.WOOD, 18);
        return production;
    }
}

