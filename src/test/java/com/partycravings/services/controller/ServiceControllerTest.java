package com.partycravings.services.controller;

import com.partycravings.services.model.Service;
import com.partycravings.services.repository.ServiceRepository;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    public void testCreateService() {
        Service service = new Service("This is a Test Service");
        service.setCategoryId("xywyue6722323");
        service.setDescription("TtsYastyatsyataysyatsytsa");
        service.setVendorId("adaeewewewewe");

        webClient.post().uri("/services")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(service), Service.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.service.id").isNotEmpty()
                .jsonPath("$.service.name").isEqualTo("This is a Test Service")
                .jsonPath("$.service.categoryId").isEqualTo("xywyue6722323")
                .jsonPath("$.service.description").isEqualTo("TtsYastyatsyataysyatsytsa")
                .jsonPath("$.service.vendorId").isEqualTo("adaeewewewewe");

    }

    @Test
    public void testGetServices() {
        webClient.get().uri("/services?categoryId=randomCategoryId")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(PagedResources.class);
    }

    @Test
    public void testGetSingleService() {
        Service service = new Service("This is a Test Service");
        service.setCategoryId("xywyue6722323");
        service.setSlug("hahaha" + System.currentTimeMillis());
        serviceRepository.save(service).block();

        webClient.get()
                .uri("/services/{slug}", Collections.singletonMap("slug", service.getSlug()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void testUpdateService() {
        Service oldService = new Service("Initial Service");
        oldService.setDescription("This is a test description");
        oldService.setVendorId("w2str5fw56gf");
        oldService.setSlug("asassaas" + System.currentTimeMillis());
        serviceRepository.save(oldService).block();

        Service newService = new Service("Updated Service");
        newService.setDescription("This is a test description");
        newService.setCategoryId("xywyue6722323");
        newService.setDescription("TtsYastyatsyataysyatsytsa");
        newService.setVendorId("adaeewewewewe");

        webClient.put()
                .uri("/services/{slug}", Collections.singletonMap("slug", oldService.getSlug()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newService), Service.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.service.name").isEqualTo("Updated Service");
    }

    @Test
    public void testDeleteService() {
        Service testService = new Service("To be deleted");
        testService.setSlug("hahasasa");
        testService.setDescription("This is a test description");
        testService.setVendorId("w2str5fw56gf");
        serviceRepository.save(testService).block();

        webClient.delete()
                .uri("/services/{slug}", Collections.singletonMap("slug",  testService.getSlug()))
                .exchange()
                .expectStatus().isOk();
    }
}
