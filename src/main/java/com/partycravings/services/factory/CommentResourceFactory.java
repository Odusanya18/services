package com.partycravings.services.factory;

import com.partycravings.services.resource.CommentResource;
import org.springframework.hateoas.Resources;

import java.util.List;

public class CommentResourceFactory {
    public static Resources<CommentResource> getResourceFor(List<CommentResource> commentResources){
        return new Resources<>(commentResources);
    }
}