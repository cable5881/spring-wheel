package com.lqb.spring.v3.core.type;

import com.lqb.spring.v3.core.io.Resource;

/**
 * @author liqibo
 * @description TODO
 * @date 2019/12/3 10:44
 **/
public class MetadataReader {

    private Resource resource;

    private final AnnotationMetadata annotationMetadata;

    public MetadataReader(Resource resource) {
        this.resource = resource;
        this.annotationMetadata = null;
    }

    public Resource getResource() {
        return resource;
    }

    public ClassMetadata getClassMetadata() {
        return this.annotationMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
