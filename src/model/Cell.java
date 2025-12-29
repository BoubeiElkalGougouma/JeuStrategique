package model;

public class Cell {
    private final int x, y;
    private final TerrainType terrainType;
    private Building building;
    private Unit unit;

    public Cell(int x, int y, TerrainType terrainType) {
        this.x = x;
        this.y = y;
        this.terrainType = terrainType;
    }

    public boolean canPlace(Building b) {
        return building == null && terrainType.isAccessible();
    }

    public boolean canMove(Unit u) {
        return unit == null && terrainType.isAccessible();
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public TerrainType getTerrainType() { return terrainType; }
    public Building getBuilding() { return building; }
    public void setBuilding(Building building) { this.building = building; }
    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }
}

