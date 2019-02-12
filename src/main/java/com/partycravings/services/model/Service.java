package com.partycravings.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class Service {
    @Id
    private String id;

    @NotNull
    private String categoryId;

    @NotBlank
    @Size(max = 50)
    private String name;

    private List<String> imageLinks;

    private List<String> features;

    private String slug;

    @NotBlank
    private String vendorId;

    @NotBlank
    private String description;

    @CreatedDate
    private Date createdAt;

    private boolean isEnabled = true;

    public Service(){}

    public Service(String name){
        setId(id);
        setName(name);
    }
}
