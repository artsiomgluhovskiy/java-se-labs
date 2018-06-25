package org.art.java_core.design.patterns.factory_method.resource_factory;

import java.time.LocalDateTime;

public class VideoResourceStrategy extends ResourceStrategyFactory.CommonResourceStrategy<VideoResourceData> {

    private String videoType;

    public VideoResourceStrategy(String videoResourcePath, String videoType) {
        super(videoResourcePath);
        this.videoType = videoType;
    }

    @Override
    public void initResource() {
        this.sourceResourceObject = new VideoResourceData(ResourceIF.TYPE.VIDEO_RESOURCE, "video/resource/data",
                "Video data creation time: " + LocalDateTime.now().toString(), 2L);
    }

    @Override
    public ResourceIF.TYPE getResourceType() {
        return ResourceIF.TYPE.VIDEO_RESOURCE;
    }

    public String getVideoType() {
        return videoType;
    }
}
