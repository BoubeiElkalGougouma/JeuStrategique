package moteur;

import model.Building;
import model.Cell;
import model.GameMap;
import model.Player;

class BuildingManager {
    private Player player;
    private GameMap map;

    public BuildingManager(Player player, GameMap map) {
        this.player = player;
        this.map = map;
    }

    public boolean constructBuilding(Building building, int x, int y) {
        if (!player.canAfford(building)) {
            return false;
        }

        Cell cell = map.getCell(x, y);
        if (cell == null || !cell.canPlace(building)) {
            return false;
        }

        player.spendResources(building);
        building.setPosition(x, y);
        building.build();
        cell.setBuilding(building);

        return true;
    }

    public boolean constructBuilding(Building building) {
        if (!player.canAfford(building)) {
            return false;
        }

        player.spendResources(building);
        building.build();

        return true;
    }
}
