package moteur;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class CombatSystem {
    private GameMap map;

    public CombatSystem(GameMap map) {
        this.map = map;
    }

    public CombatResult executeCombat(Unit attacker, Unit defender) {
        Cell attackerCell = map.getCell(attacker.getX(), attacker.getY());
        Cell defenderCell = map.getCell(defender.getX(), defender.getY());

        TerrainType attackerTerrain = attackerCell != null ? attackerCell.getTerrainType() : TerrainType.GRASS;
        TerrainType defenderTerrain = defenderCell != null ? defenderCell.getTerrainType() : TerrainType.GRASS;

        return attacker.attack(defender, attackerTerrain, defenderTerrain);
    }

    public List<Cell> getAttackableTargets(Unit unit) {
        List<Cell> targets = new ArrayList<>();
        int x = unit.getX();
        int y = unit.getY();
        int range = unit.getRange();

        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                if (Math.abs(dx) + Math.abs(dy) <= range && (dx != 0 || dy != 0)) {
                    Cell cell = map.getCell(x + dx, y + dy);
                    if (cell != null && cell.getUnit() != null) {
                        targets.add(cell);
                    }
                }
            }
        }
        return targets;
    }
}


