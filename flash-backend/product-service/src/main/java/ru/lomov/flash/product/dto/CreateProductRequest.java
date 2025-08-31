package ru.lomov.flash.product.dto;

import java.util.Set;

import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private Double price;
    private String category;
    private String type;
    private String location;
    private Set<String> images;
}
