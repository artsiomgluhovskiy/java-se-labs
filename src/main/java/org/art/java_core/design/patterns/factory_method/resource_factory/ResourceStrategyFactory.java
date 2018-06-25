package org.art.java_core.design.patterns.factory_method.resource_factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceStrategyFactory {

    public static final Logger LOG = LogManager.getLogger(ResourceStrategyFactory.class);

    public static final String VIDEO_RESOURCE_PREFIX = "video_";
    public static final String DOC_RESOURCE_PREFIX = "document_";

    public static final String YOUTUBE_VIDEO_TYPE = "Youtube";
    public static final String CHART_DOCUMENT_TYPE = "Chart";

    public static CommonResourceStrategy<? extends ResourceIF> buildResourceStrategy(String resourcePath) {
        if (resourcePath == null) {
            LOG.error("ResourceIF path is empty!");
            return null;
        }
        if (resourcePath.contains(VIDEO_RESOURCE_PREFIX)) {
            return new VideoResourceStrategy(resourcePath, YOUTUBE_VIDEO_TYPE);
        } else if (resourcePath.contains(DOC_RESOURCE_PREFIX)) {
            return new DocumentResourceStrategy(resourcePath, CHART_DOCUMENT_TYPE);
        } else {
            return null;
        }
    }

    public static abstract class CommonResourceStrategy<T extends ResourceIF> {

        protected T sourceResourceObject;
        protected String resourcePath;

        protected CommonResourceStrategy(String resourcePath) {
            this.resourcePath = resourcePath;
            initResource();
        }

        public abstract void initResource();

        public abstract ResourceIF.TYPE getResourceType();

        public T getSourceResourceObject() {
            return sourceResourceObject;
        }

        public String getResourcePath() {
            return resourcePath;
        }
    }
}
