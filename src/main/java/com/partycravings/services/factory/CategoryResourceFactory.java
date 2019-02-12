package com.partycravings.services.factory;

import com.partycravings.services.resource.CategoryResource;
import org.springframework.hateoas.Resources;

import java.util.List;

public class CategoryResourceFactory {
    public static Resources<CategoryResource> getResourceFor(List<CategoryResource> categoryResources){
        return new Resources<>(categoryResources);
    }
}
