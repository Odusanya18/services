package com.partycravings.services.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Offer {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private float discount;

    private List<String> categoryIds;
}
