package ui;

import model.*;
import moteur.Game;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class ConsoleUI {
    private Game game;
    private Scanner scanner;

    public ConsoleUI(Game game) {
        this.game = game;
        this.scanner = game.getScanner();
    }

    public void displayWelcome() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║        🎮  JEU DE STRATÉGIE - ISIL 25/26  🎮          ║");
        System.out.println("║                                                      ║");
        System.out.println("║        Projet POO - Jeu Stratégique Java             ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    public void setupPlayers() {
        System.out.print("\n Nom du Joueur 1: ");
        String name1 = scanner.nextLine();

        System.out.print(" Nom du Joueur 2: ");
        String name2 = scanner.nextLine();

        game.setupPlayers(name1, name2);

        System.out.println("\n🎮 Que la partie commence !");

        System.out.print("\n📋 Voulez-vous voir les règles du jeu ? (O/N): ");
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("O") || response.equals("OUI") || response.equals("Y") || response.equals("YES")) {
            displayRules();
        } else {
            System.out.println("\nC'est parti ! Bonne chance ! ");
        }

        pause();
    }

    private void displayRules() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              REGLES DU JEU");
        System.out.println("=".repeat(60));

        System.out.println("\n[OBJECTIF]");
        System.out.println("   -> Eliminez toutes les unites adverses pour gagner!");

        System.out.println("\n[ECONOMIE]");
        System.out.println("   * Construisez des batiments pour produire des ressources");
        System.out.println("   * Utilisez l'option 6 pour collecter manuellement");
        System.out.println("   * Chaque tour, les batiments produisent automatiquement");

        System.out.println("\n[COMBAT]");
        System.out.println("   * Soldat:   Portee 1 (melee) - Equilibre");
        System.out.println("   * Archer:   Portee 3 (distance) - Fragile mais puissant");
        System.out.println("   * Cavalier: Portee 1 (melee) - Mobile et fort");

        System.out.println("\n[STRATEGIE]");
        System.out.println("   1. Deplacez vos unites pres de l'ennemi (option 3)");
        System.out.println("   2. Verifiez les cibles a portee (option 5)");
        System.out.println("   3. Attaquez quand vous etes a portee (option 4)");

        System.out.println("\n[CARTE]");
        System.out.println("   * U1 = Vos unites          * U2 = Unites ennemies");
        System.out.println("   * B1 = Vos batiments       * B2 = Batiments ennemis");
        System.out.println("   * .. = Plaine   ~~ = Eau   ^^ = Montagne   TT = Foret");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("         Bonne chance et amusez-vous bien !");
        System.out.println("=".repeat(60));
    }

    public void displayGameState() {
        clearScreen();
        System.out.println("\n" + "═".repeat(60));
        System.out.println("   TOUR " + game.getTurnNumber());
        System.out.println("═".repeat(60));
        displayPlayerInfo(game.getCurrentPlayer());
    }

    public void displayPlayerInfo(Player player) {
        System.out.println("\n Joueur: " + player.getName());
        System.out.println("─".repeat(60));

        System.out.println("\n Ressources:");
        for (ResourceType type : ResourceType.values()) {
            System.out.printf("  %s %-12s: %5d%n",
                    type.getIcon(), type.getDisplayName(),
                    player.getResources().get(type));
        }

        System.out.println("\n  Unités (" + player.getUnits().size() + "):");
        if (player.getUnits().isEmpty()) {
            System.out.println("  Aucune unité");
        } else {
            int i = 1;
            for (Unit unit : player.getUnits()) {
                System.out.printf("  %d. %s [%d,%d] - HP:%d/%d ATK:%d DEF:%d RNG:%d MOV:%d%n",
                        i++, unit.getName(), unit.getX(), unit.getY(),
                        unit.getHp(), unit.getMaxHp(), unit.getAttack(),
                        unit.getDefense(), unit.getRange(), unit.getMovementPoints());
            }
        }

        System.out.println("\n Bâtiments (" + player.getBuildings().size() + "):");
        if (player.getBuildings().isEmpty()) {
            System.out.println("  Aucun bâtiment");
        } else {
            for (Building building : player.getBuildings()) {
                displayBuildingInfo(building);
            }
        }
    }

    public void displayBuildingInfo(Building building) {
        String status = building.isBuilt() ? "✅" : "🔨";
        System.out.print("  " + status + " " + building.getName());

        // Afficher la position si définie
        if (building.getX() >= 0 && building.getY() >= 0) {
            System.out.print(" [" + building.getX() + "," + building.getY() + "]");
        }

        Map<ResourceType, Integer> prod = building.getProduction();
        if (!prod.isEmpty()) {
            System.out.print(" → ");
            List<String> prods = new ArrayList<>();
            for (Map.Entry<ResourceType, Integer> entry : prod.entrySet()) {
                prods.add(entry.getKey().getIcon() + "+" + entry.getValue());
            }
            System.out.print(String.join(" ", prods));
        }
        System.out.println();
    }

    public int displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      MENU - " + String.format("%-20s", game.getCurrentPlayer().getName()) + "          ║");
        System.out.println("╠════════════════════════════════════════ ══╣");
        System.out.println("║  1. Construire un bâtiment                ║");
        System.out.println("║  2. Entraîner une unité                   ║");
        System.out.println("║  3. Déplacer une unité                    ║");
        System.out.println("║  4. Attaquer                              ║");
        System.out.println("║  5. Voir les cibles à portée              ║");
        System.out.println("║  6. Collecter des ressources              ║");
        System.out.println("║  7. Afficher la carte                     ║");
        System.out.println("║  8. Terminer le tour                      ║");
        System.out.println("║  9. Quitter                               ║");
        System.out.println("╚═══════════════════════════════════════ ═══╝");

        return getIntInput("Votre choix: ");
    }

    public int displayBuildingMenu() {
        System.out.println("\n  CONSTRUCTION DE BÂTIMENT");
        System.out.println("─".repeat(60));
        System.out.println("1. Centre de Commandement (💰200 🪵100 🪨100)");
        System.out.println("   → Produit: 💰+5 🪵+3 🪨+3 🍖+5 par tour");
        System.out.println();
        System.out.println("2. Mine d'Or (💰80 🪵40 🪨20)");
        System.out.println("   → Produit: 💰+15 🪨+5 par tour");
        System.out.println();
        System.out.println("3. Ferme (💰50 🪵30)");
        System.out.println("   → Produit: 🍖+20 par tour");
        System.out.println();
        System.out.println("4. Scierie (💰60 🪵20 🪨15)");
        System.out.println("   → Produit: 🪵+18 par tour");
        System.out.println();
        System.out.println("5. Carrière de Pierre (💰70 🪵35)");
        System.out.println("   → Produit: 🪨+15 par tour");
        System.out.println();
        System.out.println("6. Camp d'Entraînement (💰150 🪵80)");
        System.out.println("   → Permet d'entraîner des unités");
        System.out.println();
        System.out.println("0. Annuler");
        System.out.println("─".repeat(60));

        return getIntInput("Choisir un bâtiment: ");
    }

    public int[] getBuildingPosition() {
        System.out.println("\n Position du bâtiment:");
        int x = getIntInput("  Position X (0-19): ");
        int y = getIntInput("  Position Y (0-19): ");
        return new int[]{x, y};
    }

    public int displayUnitMenu() {
        System.out.println("\n ENTRAÎNEMENT D'UNITÉ");
        System.out.println("─".repeat(60));
        System.out.println("1. Soldat (💰50 🍖10)");
        System.out.println("   → HP:100 | ATK:15 | DEF:10 | RNG:1 | MOV:2");
        System.out.println("   → Unité équilibrée, bonne en mêlée");
        System.out.println();
        System.out.println("2. Archer (💰60 🪵20 🍖8)");
        System.out.println("   → HP:70 | ATK:20 | DEF:5 | RNG:3 | MOV:2");
        System.out.println("   → Attaque à distance, faible défense");
        System.out.println();
        System.out.println("3. Cavalier (💰100 🍖20)");
        System.out.println("   → HP:120 | ATK:25 | DEF:8 | RNG:1 | MOV:4");
        System.out.println("   → Très mobile et puissant, coûteux");
        System.out.println();
        System.out.println("0. Annuler");
        System.out.println("─".repeat(60));

        return getIntInput("Choisir une unité: ");
    }

    public int[] getUnitPosition() {
        System.out.println("\n Position de l'unité:");
        int x = getIntInput("  Position X (0-19): ");
        int y = getIntInput("  Position Y (0-19): ");
        return new int[]{x, y};
    }

    public int selectUnit(String message) {
        List<Unit> units = game.getCurrentPlayer().getUnits();

        if (units.isEmpty()) {
            displayError("Aucune unité disponible!");
            return -1;
        }

        System.out.println("\n" + message);
        System.out.println("─".repeat(50));

        // Afficher les unités disponibles
        for (int i = 0; i < units.size(); i++) {
            Unit u = units.get(i);
            System.out.printf("%d. %s [%d,%d] - HP:%d/%d%n",
                    (i + 1), u.getName(), u.getX(), u.getY(),
                    u.getHp(), u.getMaxHp());
        }
        System.out.println("─".repeat(50));

        // Demander et VALIDER l'entrée
        int choice;
        while (true) {
            choice = getIntInput("Numéro de l'unité (1-" + units.size() + "): ");

            // Convertir en index (enlever 1) et vérifier
            int index = choice - 1;

            if (index >= 0 && index < units.size()) {
                return index; // ✅ Index valide
            }

            displayError("Choix invalide ! Choisissez entre 1 et " + units.size());
        }
    }

    public int[] getTargetPosition() {
        System.out.println("\n Position cible:");
        int x = getIntInput("  Position X: ");
        int y = getIntInput("  Position Y: ");
        return new int[]{x, y};
    }

    public int selectEnemyUnit() {
        Player enemy = game.getEnemyPlayer();
        List<Unit> enemyUnits = enemy.getUnits();

        if (enemyUnits.isEmpty()) {
            displayError("L'ennemi n'a aucune unité!");
            return -1;
        }

        System.out.println("\n🎯 UNITÉS ENNEMIES:");
        System.out.println("─".repeat(50));

        for (int i = 0; i < enemyUnits.size(); i++) {
            Unit unit = enemyUnits.get(i);
            System.out.printf("%d. %s [%d,%d] - HP:%d/%d%n",
                    (i + 1), unit.getName(), unit.getX(), unit.getY(),
                    unit.getHp(), unit.getMaxHp());
        }
        System.out.println("─".repeat(50));

        // Demander et VALIDER l'entrée
        int choice;
        while (true) {
            choice = getIntInput("Choisir la cible (1-" + enemyUnits.size() + "): ");

            // Convertir en index (enlever 1) et vérifier
            int index = choice - 1;

            if (index >= 0 && index < enemyUnits.size()) {
                return index; // ✅ Index valide
            }

            displayError("Choix invalide ! Choisissez entre 1 et " + enemyUnits.size());
        }
    }
    public void displayCombatResult(CombatResult result, Unit attacker, Unit defender) {
        System.out.println("\n" + "⚔".repeat(30));
        System.out.println("              💥  COMBAT !  💥");
        System.out.println("⚔".repeat(30));

        System.out.println("\n  ATTAQUANT: " + attacker.getName());
        System.out.println("    Position: [" + attacker.getX() + "," + attacker.getY() + "]");
        System.out.printf("    HP: %d/%d | ATK: %d | DEF: %d%n",
                attacker.getHp(), attacker.getMaxHp(),
                attacker.getAttack(), attacker.getDefense());

        System.out.println("\n  DÉFENSEUR: " + defender.getName());
        System.out.println("    Position: [" + defender.getX() + "," + defender.getY() + "]");
        System.out.printf("    HP: %d/%d | ATK: %d | DEF: %d%n",
                defender.getHp(), defender.getMaxHp(),
                defender.getAttack(), defender.getDefense());

        System.out.println("\n─".repeat(60));
        System.out.println("📊 RÉSULTAT:");
        System.out.println("   " + result.getMessage());

        if (!defender.isAlive()) {
            System.out.println("\n💀 " + defender.getName() + " a été éliminé !");
        }

        System.out.println("⚔".repeat(30));
        pause();
    }

    public void displayAttackableTargets(Unit unit, List<Cell> targets) {
        System.out.println("\n CIBLES À PORTÉE");
        System.out.println("═".repeat(60));
        System.out.printf("Unité sélectionnée: %s [%d,%d]%n",
                unit.getName(), unit.getX(), unit.getY());
        System.out.printf("Portée d'attaque: %d case(s)%n", unit.getRange());
        System.out.println("─".repeat(60));

        if (targets.isEmpty()) {
            System.out.println("\n❌ Aucune cible ennemie à portée!");
            System.out.println("\n💡 Conseil:");
            System.out.println("   • Soldat/Cavalier: Portée = 1 (mêlée uniquement)");
            System.out.println("   • Archer: Portée = 3 (peut attaquer à distance)");
            System.out.println("   • Utilisez l'option 3 pour déplacer vos unités");
        } else {
            System.out.println("\n Ennemis à portée (" + targets.size() + "):");
            for (Cell cell : targets) {
                Unit target = cell.getUnit();
                int distance = Math.abs(unit.getX() - target.getX()) +
                        Math.abs(unit.getY() - target.getY());
                System.out.printf("  • %s [%d,%d] - Distance: %d - HP:%d/%d%n",
                        target.getName(), target.getX(), target.getY(),
                        distance, target.getHp(), target.getMaxHp());
            }
        }

        System.out.println("═".repeat(60));
        pause();
    }


    public void displayMap(int width, int height) {
        System.out.println("\n=== CARTE DU JEU ===");

        GameMap map = game.getMap();

        // En-tête avec numéros de colonnes
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            System.out.print(String.format("%2d ", x));
        }
        System.out.println();
        System.out.println("   +" + "---".repeat(width) + "+");

        // Afficher la carte
        for (int y = 0; y < height; y++) {
            System.out.print(String.format("%2d |", y));
            for (int x = 0; x < width; x++) {
                Cell cell = map.getCell(x, y);
                System.out.print(getCellDisplay(cell) + " ");
            }
            System.out.println("|");
        }

        System.out.println("   +" + "---".repeat(width) + "+");

        // Pied avec numéros de colonnes
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            System.out.print(String.format("%2d ", x));
        }
        System.out.println("\n");

        // Légende
        System.out.println("LEGENDE:");
        System.out.println("  .. = Plaine    ~~ = Eau       ^^ = Montagne   TT = Foret");
        System.out.println("  U1 = Votre unite              U2 = Unite ennemie");
        System.out.println("  B1 = Votre batiment           B2 = Batiment ennemi");

        // Positions détaillées
        Player currentPlayer = game.getCurrentPlayer();
        Player enemyPlayer = game.getEnemyPlayer();

        boolean hasUnits = !currentPlayer.getUnits().isEmpty() || !enemyPlayer.getUnits().isEmpty();
        boolean hasBuildings = !currentPlayer.getBuildings().isEmpty() || !enemyPlayer.getBuildings().isEmpty();

        if (hasUnits || hasBuildings) {
            System.out.println("\n");
            System.out.println("+" + "-".repeat(58) + "+");
            System.out.println("|" + " ".repeat(15) + "POSITIONS SUR LA CARTE" + " ".repeat(21) + "|");
            System.out.println("+" + "-".repeat(58) + "+");

            if (!currentPlayer.getUnits().isEmpty()) {
                System.out.println("|                                                          |");
                System.out.println("| >> VOS UNITES:                                           |");
                System.out.println("|" + "-".repeat(58) + "|");
                int i = 1;
                for (Unit unit : currentPlayer.getUnits()) {
                    String line = String.format("| %d. %-12s [%2d,%-2d] HP:%-3d/%-3d ATK:%-2d DEF:%-2d RNG:%-2d",
                            i++, unit.getName(), unit.getX(), unit.getY(),
                            unit.getHp(), unit.getMaxHp(), unit.getAttack(),
                            unit.getDefense(), unit.getRange());
                    // Compléter avec des espaces pour atteindre 59 caractères
                    int padding = 59 - line.length();
                    System.out.println(line + " ".repeat(Math.max(0, padding)) + "|");
                }
            }

            if (!enemyPlayer.getUnits().isEmpty()) {
                System.out.println("|                                                          |");
                System.out.println("| >> UNITES ENNEMIES:                                      |");
                System.out.println("|" + "-".repeat(58) + "|");
                int i = 1;
                for (Unit unit : enemyPlayer.getUnits()) {
                    String line = String.format("| %d. %-12s [%2d,%-2d] HP:%-3d/%-3d",
                            i++, unit.getName(), unit.getX(), unit.getY(),
                            unit.getHp(), unit.getMaxHp());
                    int padding = 59 - line.length();
                    System.out.println(line + " ".repeat(Math.max(0, padding)) + "|");
                }
            }

            if (!currentPlayer.getBuildings().isEmpty()) {
                System.out.println("|                                                          |");
                System.out.println("| >> VOS BATIMENTS:                                        |");
                System.out.println("|" + "-".repeat(58) + "|");
                for (Building b : currentPlayer.getBuildings()) {
                    String line = String.format("| - %-30s [%2d,%-2d]",
                            b.getName(), b.getX(), b.getY());
                    int padding = 59 - line.length();
                    System.out.println(line + " ".repeat(Math.max(0, padding)) + "|");
                }
            }

            if (!enemyPlayer.getBuildings().isEmpty()) {
                System.out.println("|                                                          |");
                System.out.println("| >> BATIMENTS ENNEMIS:                                    |");
                System.out.println("|" + "-".repeat(58) + "|");
                for (Building b : enemyPlayer.getBuildings()) {
                    String line = String.format("| - %-30s [%2d,%-2d]",
                            b.getName(), b.getX(), b.getY());
                    int padding = 59 - line.length();
                    System.out.println(line + " ".repeat(Math.max(0, padding)) + "|");
                }
            }

            System.out.println("+" + "-".repeat(58) + "+");
        }

        pause();
    }
    private String getCellDisplay(Cell cell) {
        // Priorité : Unité > Bâtiment > Terrain
        if (cell.getUnit() != null) {
            Unit unit = cell.getUnit();
            if (game.getPlayer1().getUnits().contains(unit)) {
                return "U1"; // Unité Joueur 1
            } else {
                return "U2"; // Unité Joueur 2
            }
        }

        if (cell.getBuilding() != null) {
            Building building = cell.getBuilding();
            if (game.getPlayer1().getBuildings().contains(building)) {
                return "B1"; // Bâtiment Joueur 1
            } else {
                return "B2"; // Bâtiment Joueur 2
            }
        }

        return switch(cell.getTerrainType()) {
            case GRASS -> "..";     // Plaine
            case WATER -> "~~";     // Eau
            case MOUNTAIN -> "^^";  // Montagne
            case FOREST -> "TT";    // Forêt (Trees)
        };
    }

    public void displayWinner(Player winner) {
        clearScreen();
        System.out.println("\n" + "═".repeat(60));
        System.out.println();
        System.out.println("            🏆  VICTOIRE !  🏆");
        System.out.println();
        System.out.println("═".repeat(60));
        System.out.println();
        System.out.println("       " + winner.getName().toUpperCase() + " a remporté la partie !");
        System.out.println();
        System.out.println("═".repeat(60));
        System.out.println();
        System.out.println("📊 Statistiques finales:");
        System.out.println("  • Unités restantes: " + winner.getUnits().size());
        System.out.println("  • Bâtiments construits: " + winner.getBuildings().size());
        System.out.println("  • Tours joués: " + game.getTurnNumber());
        System.out.println();
        System.out.println("═".repeat(60));
    }

    public void displaySuccess(String message) {
        System.out.println("✅ " + message);
    }

    public void displayError(String message) {
        System.out.println("❌ " + message);
    }

    public void displayInfo(String message) {
        System.out.println("ℹ️  " + message);
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrez un nombre valide: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne
        return value;
    }

    public void pause() {
        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    private void clearScreen() {
        // Pour une meilleure lisibilité
        for (int i = 0; i < 2; i++) {
            System.out.println();
        }
    }
}