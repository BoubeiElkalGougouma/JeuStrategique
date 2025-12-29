package moteur;

import model.Player;
import model.ResourceType;

import java.util.HashMap;
import java.util.Map;

class ResourceManager {
    public static void displayResourceChange(Player player, Map<ResourceType, Integer> before, Map<ResourceType, Integer> after) {
        System.out.println("\n📊 Changement de ressources:");
        for (ResourceType type : ResourceType.values()) {
            int change = after.get(type) - before.get(type);
            if (change != 0) {
                String sign = change > 0 ? "+" : "";
                System.out.printf("  %s %s: %s%d (Total: %d)%n",
                        type.getIcon(), type.getDisplayName(), sign, change, after.get(type));
            }
        }
    }

    public static Map<ResourceType, Integer> copyResources(Map<ResourceType, Integer> resources) {
        return new HashMap<>(resources);
    }
}
