package model;

import interfaces.Producible;

import java.util.Map;

public abstract class Building implements Producible {
    protected String name;
    protected int constructionTime;
    protected boolean isBuilt;
    protected int x, y;

    public Building(String name, int constructionTime) {
        this.name = name;
        this.constructionTime = constructionTime;
        this.isBuilt = false;
    }

    public abstract int getCost(ResourceType type);
    public abstract void produce();
    public abstract Map<ResourceType, Integer> getProduction();

    public void build() {
        isBuilt = true;
    }

    public String getName() { return name; }
    public boolean isBuilt() { return isBuilt; }
    public int getConstructionTime() { return constructionTime; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
