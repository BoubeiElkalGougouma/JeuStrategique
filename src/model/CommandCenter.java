package model;

import java.util.HashMap;
import java.util.Map;

public class CommandCenter extends Building {
    public CommandCenter() {
        super("Centre de Commandement", 5);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 200;
            case WOOD -> 100;
            case STONE -> 100;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        Map<ResourceType, Integer> production = new HashMap<>();
        production.put(ResourceType.GOLD, 5);
        production.put(ResourceType.WOOD, 3);
        production.put(ResourceType.STONE, 3);
        production.put(ResourceType.FOOD, 5);
        return production;
    }
}
