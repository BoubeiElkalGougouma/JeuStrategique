package moteur;

import model.Building;
import model.Cell;
import model.GameMap;
import model.Player;

public class BuildingManager {
    private Player player;
    private GameMap map;

    public BuildingManager(Player player, GameMap map) {
        this.player = player;
        this.map = map;
    }

    public boolean constructBuilding(Building building, int x, int y) {
        // Vérifier si la case existe
        Cell cell = map.getCell(x, y);
        if (cell == null) {
            return false;
        }
        if (!cell.canPlace(building)) {
            return false;
        }

        if (!player.canAfford(building)) {
            return false;
        }

        player.spendResources(building);

        building.setPosition(x, y);
        building.build();

        cell.setBuilding(building);

        return true;
    }
}
