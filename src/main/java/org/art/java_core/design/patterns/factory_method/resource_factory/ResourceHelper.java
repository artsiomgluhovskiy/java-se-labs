package org.art.java_core.design.patterns.factory_method.resource_factory;

import static org.art.java_core.design.patterns.factory_method.resource_factory.ResourceIF.TYPE.UNDEFINED;

public class ResourceHelper {

    private ResourceStrategyFactory.CommonResourceStrategy<? extends ResourceIF> resourceStrategy;

    public ResourceHelper(String resourcePath) {
        resourceStrategy = ResourceStrategyFactory.buildResourceStrategy(resourcePath);
    }

    public String getResourcePath() {
        String resourcePath = "";
        if (resourceStrategy != null) {
            resourcePath = resourceStrategy.getResourcePath();
        }
        return resourcePath;
    }

    public ResourceIF getSourceResourceObject() {
        ResourceIF sourceResourceObject = null;
        if (resourceStrategy != null) {
            sourceResourceObject = resourceStrategy.getSourceResourceObject();
        }
        return sourceResourceObject;
    }

    public boolean isSuccessfullyInitialized() {
        return resourceStrategy != null;
    }

    public ResourceIF.TYPE getResourceType() {
        return resourceStrategy != null ? resourceStrategy.getResourceType() : UNDEFINED;
    }

    public ResourceStrategyFactory.CommonResourceStrategy<? extends ResourceIF> getResourceStrategy() {
        return resourceStrategy;
    }
}
