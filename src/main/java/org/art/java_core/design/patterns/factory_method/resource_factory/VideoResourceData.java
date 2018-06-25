package org.art.java_core.design.patterns.factory_method.resource_factory;

public class VideoResourceData implements ResourceIF {

    private TYPE videoResourceType;
    private String videoDataPath;
    private String videoMetadata;

    private long videoId;

    public VideoResourceData(TYPE resourceType, String resourceDataPath, String resourceMetadata, long videoId) {
        this.videoResourceType = resourceType;
        this.videoDataPath = resourceDataPath;
        this.videoMetadata = resourceMetadata;
        this.videoId = videoId;

    }

    @Override
    public ResourceIF.TYPE getResourceType() {
        return videoResourceType;
    }

    @Override
    public String getResourceDataPath() {
        return videoDataPath;
    }

    @Override
    public String getResourceMetadata() {
        return videoMetadata;
    }

    public long getVideoId() {
        return videoId;
    }

    @Override
    public String toString() {
        return "VideoResourceData{" +
                "videoResourceType=" + videoResourceType +
                ", videoDataPath='" + videoDataPath + '\'' +
                ", videoMetadata='" + videoMetadata + '\'' +
                ", videoId='" + videoId + '\'' +
                '}';
    }
}
