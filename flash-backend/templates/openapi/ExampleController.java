# OpenAPI Controller Example for Flash-Spring Microservices
# Copy and customize for each service's REST controller

package ru.lomov.flashbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Example REST Controller with OpenAPI documentation annotations.
 * Customize endpoints, DTOs, and business logic for your service.
 */
@RestController
@RequestMapping("/api/v1/example")
@RequiredArgsConstructor
@Tag(name = "Example", description = "Example API endpoints for demonstration")
public class ExampleController {

    // private final ExampleService exampleService;

    @GetMapping
    @Operation(
        summary = "Get all items",
        description = "Retrieves a list of all items with pagination support",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved items",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = List.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing token",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content)
    })
    public ResponseEntity<List<Object>> getAllItems(
        @Parameter(description = "Page number (0-based)", example = "0")
        @RequestParam(defaultValue = "0") int page,
        
        @Parameter(description = "Number of items per page", example = "20")
        @RequestParam(defaultValue = "20") int size,
        
        @Parameter(description = "Sort field", example = "createdAt")
        @RequestParam(defaultValue = "createdAt") String sortBy,
        
        @Parameter(description = "Sort direction (asc/desc)", example = "desc")
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        // return ResponseEntity.ok(exampleService.findAll(page, size, sortBy, sortDir));
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get item by ID",
        description = "Retrieves a single item by its unique identifier",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item found",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "404", description = "Item not found",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content)
    })
    public ResponseEntity<Object> getItemById(
        @Parameter(description = "Unique identifier of the item", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
        @PathVariable String id
    ) {
        // return ResponseEntity.ok(exampleService.findById(id));
        return ResponseEntity.ok(new Object());
    }

    @PostMapping
    @Operation(
        summary = "Create new item",
        description = "Creates a new item with the provided data",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Item created successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content)
    })
    public ResponseEntity<Object> createItem(
        @Parameter(description = "Item data to create", required = true)
        @RequestBody Object request
    ) {
        // return ResponseEntity.status(HttpStatus.CREATED).body(exampleService.create(request));
        return ResponseEntity.ok(new Object());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Update existing item",
        description = "Updates an existing item with new data",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item updated successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Object.class))),
        @ApiResponse(responseCode = "404", description = "Item not found",
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input data",
            content = @Content)
    })
    public ResponseEntity<Object> updateItem(
        @Parameter(description = "Unique identifier of the item", required = true)
        @PathVariable String id,
        
        @Parameter(description = "Updated item data", required = true)
        @RequestBody Object request
    ) {
        // return ResponseEntity.ok(exampleService.update(id, request));
        return ResponseEntity.ok(new Object());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete item",
        description = "Deletes an item by its unique identifier",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Item not found",
            content = @Content),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content)
    })
    public ResponseEntity<Void> deleteItem(
        @Parameter(description = "Unique identifier of the item", required = true)
        @PathVariable String id
    ) {
        // exampleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
