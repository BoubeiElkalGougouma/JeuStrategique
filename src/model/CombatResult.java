package model;

public class CombatResult {
    private final boolean success;
    private final int damage;
    private final String message;

    public CombatResult(boolean success, int damage, String message) {
        this.success = success;
        this.damage = damage;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public int getDamage() { return damage; }
    public String getMessage() { return message; }
}