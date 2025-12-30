package interfaces;

import model.ResourceType;

import java.util.Map;

public interface Producible {
    void produce();
    Map<ResourceType, Integer> getProduction();
}
