package com.partycravings.services.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partycravings.services.controller.CategoryController;
import com.partycravings.services.controller.ServiceController;
import com.partycravings.services.model.Category;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CategoryResource extends ResourceSupport {
    @JsonProperty
    private Category category;

    public CategoryResource(Category category){
        this.category = category;
        add(linkTo(CategoryController.class).withRel("categories"));
        add(linkTo(methodOn(ServiceController.class).getServicesByCategory(category.getId(), null, null, null)).withRel("services"));
    }
}
