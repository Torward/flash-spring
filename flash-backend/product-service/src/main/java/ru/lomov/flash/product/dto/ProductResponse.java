package ru.lomov.flash.product.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String type;
    private String location;
    private Set<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
