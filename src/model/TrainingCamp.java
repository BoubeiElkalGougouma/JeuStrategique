package model;


import java.util.HashMap;
import java.util.Map;

public class TrainingCamp extends Building{
    public TrainingCamp() {
        super("Camp d'Entraînement", 3);
    }

    @Override
    public int getCost(ResourceType type) {
        return switch(type) {
            case GOLD -> 150;
            case WOOD -> 80;
            default -> 0;
        };
    }

    @Override
    public void produce() {}

    @Override
    public Map<ResourceType, Integer> getProduction() {
        return new HashMap<>(); // Ne produit pas de ressources
    }

}

