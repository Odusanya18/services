package com.partycravings.services.controller;

import com.partycravings.services.factory.PagedServiceResourceFactory;
import com.partycravings.services.model.Service;
import com.partycravings.services.repository.ServiceRepository;
import com.partycravings.services.resource.ServiceResource;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@AllArgsConstructor
public class ServiceController {
    private final ServiceRepository serviceRepository;

    @PostMapping
    public Mono<ServiceResource> createService(@Valid @RequestBody Service service){
        return serviceRepository.save(service)
                .map(ServiceResource::new);
    }

    /**
     * Service are Sort.By <code>createdAt</code> always, <code>if (orderBy != null)</code>, <code>orderBy</code> with <code>Sort.Direction</code>
     */
    @GetMapping
    public Mono<PagedResources<ServiceResource>> getServicesByCategory(@RequestParam(name = "categoryId")String categoryId, @RequestParam(name = "orderBy", required = false)String orderBy, @RequestParam(name = "direction", defaultValue = "ASC")String direction, @RequestParam(name = "page", defaultValue = "1")Long page){
        Sort sort = Sort.by("createdAt").descending();
        Optional<String> orderByOptional = Optional.ofNullable(orderBy);
        orderByOptional.map(orderBy1 -> sort.and(Sort.by(Sort.Direction.fromString(direction), orderBy1)));

        String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        Mono<Long> serviceCount = serviceRepository.countByCategoryIdAndIsEnabledIsTrue(categoryId);
        Mono<List<ServiceResource>> serviceResourceList = serviceRepository.findByCategoryIdAndIsEnabledIsTrue(categoryId, PagedServiceResourceFactory.getSortFor(sort, page.intValue()))
                .map(ServiceResource::new)
                .collectList();
        return serviceCount.flatMap(count ->
                serviceResourceList.map(serviceResources -> PagedServiceResourceFactory.getPagedResourceFor(serviceResources, page, count))
                        .map(serviceResources ->   PagedServiceResourceFactory.addCurrentLinkTo(serviceResources, uriString)));
    }

    @GetMapping("/{slug}")
    public Mono<ResponseEntity<ServiceResource>> getService(@PathVariable(name = "slug")String slug){
        return serviceRepository.findBySlug(slug)
                .map(service -> ResponseEntity.ok(new ServiceResource(service)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{slug}")
    public Mono<ResponseEntity<ServiceResource>> updateService(@PathVariable(name = "slug")String slug, @Valid @RequestBody Service service){
        return serviceRepository.findBySlug(slug)
                .flatMap(existingService -> {
                    existingService.setName(service.getName());
                    existingService.setDescription(service.getDescription());
                    return serviceRepository.save(existingService)
                            .map(ServiceResource::new);
                })
                .map(updatedServiceResource -> new ResponseEntity<>(updatedServiceResource, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{slug}")
    public Mono<ResponseEntity<Void>> deleteService(@PathVariable(name = "slug")String slug){
        return serviceRepository.findBySlug(slug)
                .flatMap(service -> serviceRepository.delete(service)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
