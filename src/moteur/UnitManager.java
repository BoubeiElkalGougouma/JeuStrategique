package moteur;

import model.Cell;
import model.GameMap;
import model.Player;
import model.Unit;

class UnitManager {
    private Player player;
    private GameMap map;

    public UnitManager(Player player, GameMap map) {
        this.player = player;
        this.map = map;
    }

    public boolean trainUnit(Unit unit, int x, int y) {
        if (!player.canAfford(unit)) {
            return false;
        }

        Cell cell = map.getCell(x, y);
        if (cell == null || !cell.canMove(unit)) {
            return false;
        }

        player.spendResources(unit);
        unit.setPosition(x, y);
        cell.setUnit(unit);

        return true;
    }
}
