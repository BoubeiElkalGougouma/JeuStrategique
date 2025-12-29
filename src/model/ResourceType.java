package model;

public enum ResourceType {
    GOLD("Or", "💰"),
    WOOD("Bois", "🪵"),
    STONE("Pierre", "🪨"),
    FOOD("Nourriture", "🍖");

    private final String displayName;
    private final String icon;

    ResourceType(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }

    public String getDisplayName() { return displayName; }
    public String getIcon() { return icon; }
}
