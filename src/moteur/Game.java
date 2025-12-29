// ==================== PACKAGE: com.isil.game.moteur ====================
package moteur;

import model.*;
import ui.ConsoleUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    // Composants du jeu
    private GameMap map;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    // Managers
    private CombatSystem combatSystem;
    private TurnManager turnManager;
    private BuildingManager buildingManager1;
    private BuildingManager buildingManager2;
    private UnitManager unitManager1;
    private UnitManager unitManager2;

    // UI
    private Scanner scanner;
    private ConsoleUI ui;

    // ========== CONSTRUCTEURS ==========

    public Game() {
        this(20, 20);
    }

    public Game(int mapWidth, int mapHeight) {
        this.map = new GameMap(mapWidth, mapHeight);
        this.player1 = new Player("Joueur1");
        this.player2 = new Player("Joueur2");
        this.currentPlayer = player1;

        // Initialiser les managers
        this.combatSystem = new CombatSystem(map);
        this.turnManager = new TurnManager(player1, player2);
        this.buildingManager1 = new BuildingManager(player1, map);
        this.buildingManager2 = new BuildingManager(player2, map);
        this.unitManager1 = new UnitManager(player1, map);
        this.unitManager2 = new UnitManager(player2, map);

        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleUI(this);

        // Donner des ressources de départ
        initializePlayerResources(player1);
        initializePlayerResources(player2);
    }

    private void initializePlayerResources(Player player) {
        player.getResources().put(ResourceType.GOLD, 500);
        player.getResources().put(ResourceType.WOOD, 400);
        player.getResources().put(ResourceType.STONE, 300);
        player.getResources().put(ResourceType.FOOD, 300);
    }

    // ========== DÉMARRAGE DU JEU ==========

    public void start() {
        ui.displayWelcome();
        ui.setupPlayers();

        // Boucle principale du jeu
        gameLoop();

        // Afficher le gagnant
        if (getWinner() != null) {
            ui.displayWinner(getWinner());
        }

        closeScanner();
    }

    private void gameLoop() {
        while (!isGameOver()) {
            ui.displayGameState();

            int choice = ui.displayMenu();

            switch (choice) {
                case 1 -> handleBuildingConstruction();
                case 2 -> handleUnitTraining();
                case 3 -> handleUnitMovement();
                case 4 -> handleCombat();
                case 5 -> handleViewTargets();
                case 6 -> collectResources();
                case 7 -> ui.displayMap(20, 20);
                case 8 -> nextTurn();
                case 9 -> {
                    ui.displayInfo("Merci d'avoir joué!");
                    return;
                }
                default -> ui.displayError("Choix invalide!");
            }
        }
    }

    // ========== GESTION DE CONSTRUCTION ==========

    private void handleBuildingConstruction() {
        int type = ui.displayBuildingMenu();
        if (type > 0) {
            if (buildBuilding(type)) {
                ui.displaySuccess("Bâtiment construit!");
            } else {
                ui.displayError("Construction impossible! (Ressources insuffisantes)");
            }
        }
    }

    public boolean buildBuilding(int buildingType) {
        Building building = createBuilding(buildingType);
        if (building == null) return false;

        BuildingManager manager = getCurrentBuildingManager();
        return manager.constructBuilding(building);
    }

    private Building createBuilding(int type) {
        return switch (type) {
            case 1 -> new CommandCenter();
            case 2 -> new Mine();
            case 3 -> new Farm();
            case 4 -> new Sawmill();
            case 5 -> new StoneMine();
            case 6 -> new TrainingCamp();
            default -> null;
        };
    }

    // ========== GESTION DES UNITÉS ==========

    private void handleUnitTraining() {
        int type = ui.displayUnitMenu();
        if (type > 0) {
            int[] pos = ui.getUnitPosition();
            if (trainUnit(type, pos[0], pos[1])) {
                ui.displaySuccess("Unité entraînée!");
            } else {
                ui.displayError("Entraînement impossible! (Ressources insuffisantes ou case occupée)");
            }
        }
    }

    public boolean trainUnit(int unitType, int x, int y) {
        Unit unit = createUnit(unitType);
        if (unit == null) return false;

        UnitManager manager = getCurrentUnitManager();
        return manager.trainUnit(unit, x, y);
    }

    private Unit createUnit(int type) {
        return switch (type) {
            case 1 -> new Soldier();
            case 2 -> new Archer();
            case 3 -> new Cavalry();
            default -> null;
        };
    }

    // ========== GESTION DU MOUVEMENT ==========

    private void handleUnitMovement() {
        int unitIdx = ui.selectUnit("👣 Quelle unité déplacer?");
        if (unitIdx < 0) return;

        int[] pos = ui.getTargetPosition();
        MoveResult result = moveUnit(unitIdx, pos[0], pos[1]);
        ui.displayInfo(result.getMessage());
    }

    public MoveResult moveUnit(int unitIndex, int x, int y) {
        if (unitIndex < 0 || unitIndex >= currentPlayer.getUnits().size()) {
            return new MoveResult(false, "Unité invalide!");
        }

        Unit unit = currentPlayer.getUnits().get(unitIndex);
        return unit.moveTo(x, y, map);
    }

    // ========== GESTION DU COMBAT ==========

    private void handleCombat() {
        int attackerIdx = ui.selectUnit("⚔️  Quelle unité attaque?");
        if (attackerIdx < 0) return;

        int defenderIdx = ui.selectEnemyUnit();
        if (defenderIdx < 0) return;

        // Sauvegarder les références avant le combat
        Unit attacker = currentPlayer.getUnits().get(attackerIdx);
        Unit defender = getEnemyPlayer().getUnits().get(defenderIdx);

        CombatResult result = attack(attackerIdx, defenderIdx);

        // Afficher le résultat avec les unités sauvegardées
        ui.displayCombatResult(result, attacker, defender);
    }

    private void handleViewTargets() {
        if (currentPlayer.getUnits().isEmpty()) {
            ui.displayError("Vous n'avez aucune unité!");
            return;
        }

        int unitIdx = ui.selectUnit("🎯 Voir les cibles à portée de quelle unité?");
        if (unitIdx < 0) return;

        Unit unit = currentPlayer.getUnits().get(unitIdx);
        List<Cell> targets = getAttackableTargets(unit);

        ui.displayAttackableTargets(unit, targets);
    }

    public CombatResult attack(int attackerIndex, int defenderIndex) {
        Player enemy = getEnemyPlayer();

        if (attackerIndex < 0 || attackerIndex >= currentPlayer.getUnits().size()) {
            return new CombatResult(false, 0, "Attaquant invalide!");
        }

        if (defenderIndex < 0 || defenderIndex >= enemy.getUnits().size()) {
            return new CombatResult(false, 0, "Cible invalide!");
        }

        Unit attacker = currentPlayer.getUnits().get(attackerIndex);
        Unit defender = enemy.getUnits().get(defenderIndex);

        return combatSystem.executeCombat(attacker, defender);
    }

    public void collectResources() {
        Map<ResourceType, Integer> before = new java.util.HashMap<>(currentPlayer.getResources());

        currentPlayer.collectResources();

        System.out.println("\n💰 Ressources collectées:");
        boolean hasProduction = false;
        for (ResourceType type : ResourceType.values()) {
            int gained = currentPlayer.getResources().get(type) - before.get(type);
            if (gained > 0) {
                System.out.printf("  %s +%d %s%n", type.getIcon(), gained, type.getDisplayName());
                hasProduction = true;
            }
        }

        if (!hasProduction) {
            ui.displayInfo("Aucun bâtiment de production actif!");
        }
    }

    public void nextTurn() {
        turnManager.nextTurn();
        switchPlayer();
        ui.displayInfo("🔄 C'est au tour de " + currentPlayer.getName());
        ui.pause();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public boolean isGameOver() {
        if (getTurnNumber() < 5) return false;
        return player1.getUnits().isEmpty() || player2.getUnits().isEmpty();
    }

    public Player getWinner() {
        if (player2.getUnits().isEmpty() && !player1.getUnits().isEmpty()) {
            return player1;
        }
        if (player1.getUnits().isEmpty() && !player2.getUnits().isEmpty()) {
            return player2;
        }
        return null;
    }

    public GameMap getMap() { return map; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public Player getEnemyPlayer() {
        return (currentPlayer == player1) ? player2 : player1;
    }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getTurnNumber() { return turnManager.getCurrentTurn(); }
    public Scanner getScanner() { return scanner; }

    public List<Cell> getAttackableTargets(Unit unit) {
        return combatSystem.getAttackableTargets(unit);
    }

    private BuildingManager getCurrentBuildingManager() {
        return (currentPlayer == player1) ? buildingManager1 : buildingManager2;
    }

    private UnitManager getCurrentUnitManager() {
        return (currentPlayer == player1) ? unitManager1 : unitManager2;
    }

    public void setupPlayers(String name1, String name2) {
        player1.setName(name1);
        player2.setName(name2);
    }

    public void closeScanner() {
        scanner.close();
    }
}