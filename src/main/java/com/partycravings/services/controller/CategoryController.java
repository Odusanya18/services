package com.partycravings.services.controller;

import com.partycravings.services.factory.CategoryResourceFactory;
import com.partycravings.services.model.Category;
import com.partycravings.services.repository.CategoryRepository;
import com.partycravings.services.resource.CategoryResource;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    public Mono<Resources<CategoryResource>> getCategories(){
        return categoryRepository.findAll()
                .map(CategoryResource::new)
                .collectList()
                .map(CategoryResourceFactory::getResourceFor);
    }

    @PostMapping
    public Mono<CategoryResource>  createCategory(@Valid @RequestBody Category category){
        return categoryRepository.save(category)
                .map(CategoryResource::new);
    }

    @PutMapping("/{slug}")
    public Mono<ResponseEntity<CategoryResource>> updateCategory(@PathVariable(name = "slug")String slug, @Valid @RequestBody Category category){
        return categoryRepository.findBySlug(slug)
                .flatMap(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory)
                            .map(CategoryResource::new);
                })
                .map(updatedCategoryResource -> new ResponseEntity<>(updatedCategoryResource, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{slug}")
    public Mono<ResponseEntity<Void>> deleteCategory(@PathVariable(name = "slug")String slug){
        return categoryRepository.findBySlug(slug)
                .flatMap(category -> categoryRepository.delete(category)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
