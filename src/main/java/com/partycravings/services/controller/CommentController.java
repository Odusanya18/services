package com.partycravings.services.controller;

import com.partycravings.services.factory.CommentResourceFactory;
import com.partycravings.services.model.Comment;
import com.partycravings.services.repository.CommentRepository;
import com.partycravings.services.resource.CommentResource;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @GetMapping
    public Mono<Resources<CommentResource>> getComments(@RequestParam(name = "serviceId")String serviceId){
        return commentRepository.findCommentByServiceIdOrderByCreatedAtDesc(serviceId)
                .map(CommentResource::new)
                .collectList()
                .map(CommentResourceFactory::getResourceFor);
    }

    @PostMapping
    public Mono<CommentResource> createComment(@Valid @RequestBody Comment comment){
        return commentRepository.save(comment)
                .map(CommentResource::new);

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CommentResource>> updateComment(@PathVariable(name = "id")String id, @Valid @RequestBody Comment comment){

        return commentRepository.findById(id)
                .flatMap(existingComment -> {
                    existingComment.setText(comment.getText());
                    existingComment.setRating(comment.getRating());
                    return commentRepository.save(existingComment)
                            .map(CommentResource::new);
                })
                .map(updatedCommentResource -> new ResponseEntity<>(updatedCommentResource, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable(name = "id")String id){
        return commentRepository.findById(id)
                .flatMap(comment -> commentRepository.delete(comment)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
