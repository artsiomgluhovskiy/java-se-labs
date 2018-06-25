package org.art.java_core.design.patterns.factory_method.resource_factory;

public interface ResourceIF {

    enum TYPE {VIDEO_RESOURCE, DOCUMENT_RESOURCE, UNDEFINED}

    TYPE getResourceType();

    String getResourceDataPath();

    String getResourceMetadata();
}
