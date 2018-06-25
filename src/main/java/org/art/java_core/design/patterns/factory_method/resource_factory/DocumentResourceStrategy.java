package org.art.java_core.design.patterns.factory_method.resource_factory;

import java.time.LocalDateTime;

public class DocumentResourceStrategy extends ResourceStrategyFactory.CommonResourceStrategy<DocumentResourceData> {

    private String documentType;

    public DocumentResourceStrategy(String documentResourcePath, String documentType) {
        super(documentResourcePath);
        this.documentType = documentType;
    }

    @Override
    public void initResource() {
        this.sourceResourceObject = new DocumentResourceData(ResourceIF.TYPE.DOCUMENT_RESOURCE, "document/resource/data",
                "Document data creation time: " + LocalDateTime.now().toString(), 1L);
    }

    @Override
    public ResourceIF.TYPE getResourceType() {
        return ResourceIF.TYPE.DOCUMENT_RESOURCE;
    }

    public String getDocumentType() {
        return documentType;
    }
}
