package ru.lomov.flashbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.lomov.flashbackend.dto.ApiResponse;
import ru.lomov.flashbackend.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ApiResponse<String>>> getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id)
                .map(productJson -> ResponseEntity.ok(ApiResponse.success(productJson)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }
}
