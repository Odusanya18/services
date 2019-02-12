package com.partycravings.services.factory;

import com.partycravings.services.resource.ServiceResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;

import java.util.List;

public class PagedServiceResourceFactory {
    private final static Integer maxPerPage = 10;
    public static PagedResources<ServiceResource> getPagedResourceFor(List<ServiceResource> serviceResources, Long page, Long totalCount){
        return new PagedResources<>(serviceResources, new PagedResources.PageMetadata(maxPerPage, page, totalCount, totalCount/ maxPerPage));
    }

    public static Pageable getSortFor(Sort sort, Integer page){
        return PageRequest.of(page, maxPerPage, sort);
    }

    public static PagedResources<ServiceResource> addCurrentLinkTo(PagedResources<ServiceResource> serviceResources, String uriString){
        serviceResources.add(new Link(uriString, "self"));
        return serviceResources;
    }
}
