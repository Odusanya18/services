package com.partycravings.services.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class Comment {
    @Id
    private String id;
    @NotBlank
    private int rating;
    @NotBlank
    private String text;
    @CreatedDate
    private Date createdAt;
    private String serviceId;
    @NotBlank
    private String userId;
}
