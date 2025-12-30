package moteur;

import model.Player;

public class TurnManager {
    private int currentTurn;
    private Player player1;
    private Player player2;

    public TurnManager(Player player1, Player player2) {
        this.currentTurn = 1;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void nextTurn() {
        currentTurn++;
        player1.collectResources();
        player2.collectResources();

        removeDeadUnits(player1);
        removeDeadUnits(player2);
    }

    private void removeDeadUnits(Player player) {
        player.getUnits().removeIf(unit -> !unit.isAlive());
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}