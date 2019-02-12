package com.partycravings.services.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
public class Category {
    @Id
    private String id;

    @NotBlank
    private String name;
    private String slug;
    private String childId;
    private String parentId;

    public Category(){
    }

    public Category(String name){
        setId(id);
        setName(name);
    }
}
