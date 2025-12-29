package moteur;

import model.Player;

public class TurnManager {
    private int currentTurn;
    private Player currentPlayer;
    private Player enemyPlayer;

    public TurnManager(Player player1, Player player2) {
        this.currentTurn = 1;
        this.currentPlayer = player1;
        this.enemyPlayer = player2;
    }

    public void nextTurn() {
        currentTurn++;
        currentPlayer.collectResources();
        enemyPlayer.collectResources();
        removeDeadUnits(currentPlayer);
        removeDeadUnits(enemyPlayer);
    }

    private void removeDeadUnits(Player player) {
        player.getUnits().removeIf(unit -> !unit.isAlive());
    }

    public int getCurrentTurn() { return currentTurn; }

    public void endTurn() {
        removeDeadUnits(currentPlayer);
        removeDeadUnits(enemyPlayer);
    }

    public boolean checkVictoryCondition() {
        return enemyPlayer.getUnits().isEmpty() || currentPlayer.getUnits().isEmpty();
    }

    public Player getWinner() {
        if (enemyPlayer.getUnits().isEmpty()) {
            return currentPlayer;
        } else if (currentPlayer.getUnits().isEmpty()) {
            return enemyPlayer;
        }
        return null;
    }
}

