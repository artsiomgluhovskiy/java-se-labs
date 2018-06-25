package org.art.java_core.design.patterns.factory_method.resource_factory;

public class DocumentResourceData implements ResourceIF {

    private TYPE docResourceType;
    private String docDataPath;
    private String docMetadata;

    private long documentId;

    public DocumentResourceData(TYPE resourceType, String resourceDataPath, String resourceMetadata, long documentId) {
        this.docResourceType = resourceType;
        this.docDataPath = resourceDataPath;
        this.docMetadata = resourceMetadata;
        this.documentId = documentId;
    }

    @Override
    public ResourceIF.TYPE getResourceType() {
        return docResourceType;
    }

    @Override
    public String getResourceDataPath() {
        return docDataPath;
    }

    @Override
    public String getResourceMetadata() {
        return docMetadata;
    }

    public long getDocumentId() {
        return documentId;
    }

    @Override
    public String toString() {
        return "DocumentResourceData{" +
                "docResourceType=" + docResourceType +
                ", docDataPath='" + docDataPath + '\'' +
                ", docMetadata='" + docMetadata + '\'' +
                ", documentId='" + documentId + '\'' +
                '}';
    }
}
