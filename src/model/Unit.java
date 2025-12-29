package model;

public abstract class Unit {
    protected String name;
    protected int hp;
    public int maxHp;
    protected int attack, defense;
    protected int range;
    protected int movementPoints;
    protected int x, y;

    public Unit(String name, int hp, int attack, int defense, int range, int movementPoints) {
        this.name = name;
        this.hp = this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.range = range;
        this.movementPoints = movementPoints;
    }
    public int getMaxHp() { return maxHp; }
    public abstract int getCost(ResourceType type);

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        hp -= actualDamage;
        if (hp < 0) hp = 0;
    }

    public CombatResult attack(Unit target, TerrainType attackerTerrain, TerrainType defenderTerrain) {
        int distance = Math.abs(this.x - target.x) + Math.abs(this.y - target.y);

        if (distance > range) {
            return new CombatResult(false, 0, "Cible hors de portée!");
        }

        double terrainBonus = getTerrainBonus(attackerTerrain);
        double defenseBonus = target.getTerrainDefenseBonus(defenderTerrain);

        double rawDamage = attack * (0.8 + Math.random() * 0.4) * terrainBonus;
        int finalDamage = Math.max(1, (int)(rawDamage - (target.defense * defenseBonus)));

        target.takeDamage(finalDamage);

        if (target.isAlive() && distance <= target.range) {
            int counterDamage = (int)(target.attack * 0.5 * (0.8 + Math.random() * 0.4));
            this.takeDamage(counterDamage);
            return new CombatResult(true, finalDamage,
                    "Dégâts infligés: " + finalDamage + " | Contre-attaque subie: " + counterDamage);
        }

        return new CombatResult(true, finalDamage,
                "Dégâts infligés: " + finalDamage + (target.isAlive() ? "" : " | Ennemi éliminé!"));
    }

    private double getTerrainBonus(TerrainType terrain) {
        return switch(terrain) {
            case MOUNTAIN -> 1.2;
            case FOREST -> 1.1;
            case GRASS -> 1.0;
            case WATER -> 0.8;
        };
    }

    private double getTerrainDefenseBonus(TerrainType terrain) {
        return switch(terrain) {
            case MOUNTAIN -> 1.3;
            case FOREST -> 1.2;
            case GRASS -> 1.0;
            case WATER -> 0.9;
        };
    }

    public MoveResult moveTo(int targetX, int targetY, GameMap map) {
        Cell targetCell = map.getCell(targetX, targetY);

        if (targetCell == null) {
            return new MoveResult(false, "Case invalide!");
        }

        if (!targetCell.canMove(this)) {
            return new MoveResult(false, "Case occupée ou inaccessible!");
        }

        int distance = Math.abs(this.x - targetX) + Math.abs(this.y - targetY);
        if (distance > movementPoints) {
            return new MoveResult(false, "Pas assez de points de mouvement!");
        }

        Cell oldCell = map.getCell(this.x, this.y);
        if (oldCell != null) {
            oldCell.setUnit(null);
        }

        this.x = targetX;
        this.y = targetY;
        targetCell.setUnit(this);

        return new MoveResult(true, "Déplacement réussi!");
    }

    public boolean isAlive() { return hp > 0; }

    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getRange() { return range; }
    public int getMovementPoints() { return movementPoints; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}