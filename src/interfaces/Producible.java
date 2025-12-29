package interfaces;

import model.ResourceType;

import java.util.Map;

interface Producible {
    void produce();
    Map<ResourceType, Integer> getProduction();
}
