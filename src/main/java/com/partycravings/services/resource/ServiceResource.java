package com.partycravings.services.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partycravings.services.controller.CategoryController;
import com.partycravings.services.controller.CommentController;
import com.partycravings.services.controller.ServiceController;
import com.partycravings.services.model.Service;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ServiceResource extends ResourceSupport {
    @JsonProperty
    private final Service service;

    @NotNull
    public ServiceResource(final Service service) {
        this.service = service;
        add(linkTo(ServiceController.class).withRel("services"));
        add(linkTo(methodOn(ServiceController.class).getService(service.getSlug())).withSelfRel());
        add(linkTo(methodOn(CommentController.class).getComments(service.getSlug())).withRel("comments"));
    }
}