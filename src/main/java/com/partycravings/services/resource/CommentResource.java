package com.partycravings.services.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.partycravings.services.controller.CommentController;
import com.partycravings.services.model.Comment;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CommentResource extends ResourceSupport {
    @JsonProperty
    private Comment comment;

    public CommentResource(Comment comment){
        this.comment = comment;
        add(linkTo(methodOn(CommentController.class).getComments(comment.getServiceId())).withRel("comments"));
    }
}
