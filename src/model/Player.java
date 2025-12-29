package model;

import java.util.*;

public class Player {
    private String name;
    private Map<ResourceType, Integer> resources;
    private List<Unit> units;
    private List<Building> buildings;

    public Player(String name) {
        this.name = name;
        this.resources = new HashMap<>();
        this.units = new ArrayList<>();
        this.buildings = new ArrayList<>();

        // Ressources initiales
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, 150);
        }
    }

    public boolean canAfford(Unit unit) {
        for (ResourceType type : ResourceType.values()) {
            if (resources.get(type) < unit.getCost(type)) {
                return false;
            }
        }
        return true;
    }

    public boolean canAfford(Building building) {
        for (ResourceType type : ResourceType.values()) {
            if (resources.get(type) < building.getCost(type)) {
                return false;
            }
        }
        return true;
    }

    public void addResource(ResourceType type, int amount) {
        resources.put(type, resources.get(type) + amount);
    }

    public void spendResources(Unit unit) {
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, resources.get(type) - unit.getCost(type));
        }
        units.add(unit);
    }

    public void spendResources(Building building) {
        for (ResourceType type : ResourceType.values()) {
            resources.put(type, resources.get(type) - building.getCost(type));
        }
        buildings.add(building);
    }

    public void collectResources() {
        for (Building building : buildings) {
            if (building.isBuilt()) {
                building.produce();
                Map<ResourceType, Integer> production = building.getProduction();
                for (Map.Entry<ResourceType, Integer> entry : production.entrySet()) {
                    addResource(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public String getName() { return name; }
    public Map<ResourceType, Integer> getResources() { return resources; }
    public List<Unit> getUnits() { return units; }
    public List<Building> getBuildings() { return buildings; }

    public void setName(String name) {
        this.name = name;
    }
}
