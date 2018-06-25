package org.art.java_core.design.patterns.factory_method.resource_factory;

/**
 * Factory method (from GoF) - code example.
 * Resource factory implementation (with elements of Strategy pattern).
 */
public class ResourceFactoryTest {

    public static void main(String[] args) {

        ResourceHelper videoHelper = new ResourceHelper("video_youtube_1");
        if (videoHelper.isSuccessfullyInitialized()) {
            String videoResourcePath = videoHelper.getResourcePath();
            ResourceIF videoSourceResourceObject = videoHelper.getSourceResourceObject();
            System.out.println("Resource type: " + videoHelper.getResourceType());
            System.out.println("Video resource path: " + videoResourcePath);
            System.out.println("Video source resource object: " + videoSourceResourceObject);
        }

        ResourceHelper documentHelper = new ResourceHelper("document_chart_1");
        if (documentHelper.isSuccessfullyInitialized()) {
            String documentResourcePath = documentHelper.getResourcePath();
            ResourceIF documentSourceResourceObject = documentHelper.getSourceResourceObject();
            System.out.println("Resource type: " + documentHelper.getResourceType());
            System.out.println("Document resource path: " + documentResourcePath);
            System.out.println("Document source resource object: " + documentSourceResourceObject);
        }
    }
}
