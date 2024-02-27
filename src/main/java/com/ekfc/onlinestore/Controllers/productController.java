package com.ekfc.onlinestore.Controllers;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ekfc.onlinestore.Models.products.ProductsResponse;
import com.ekfc.onlinestore.Models.products.products;
import com.ekfc.onlinestore.Models.products.productsDto;
import com.ekfc.onlinestore.Services.ProductsService;

@RestController
@RequestMapping("api/products")
public class productController {
    private final ProductsService productsService;

    public productController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> getProducts() {
        try {
            List<products> allProducts = productsService.getAllProducts();
            return ResponseEntity.ok(new ProductsResponse(200, "All Products", allProducts));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductsResponse(500, "Internal Server Error", Collections.<products>emptyList()));
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<ProductsResponse> getProductById(@PathVariable int id) {
        try {
            products product = productsService.getSpecificProduct(id);
            return ResponseEntity.ok(new ProductsResponse(200, "Product by id", Collections.singletonList(product)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductsResponse(404, "Product not found", Collections.<products>emptyList()));
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ProductsResponse> getProductById(@PathVariable String category) {
        try {
            List<products> product = productsService.getProductsByCategory(category);
            return ResponseEntity.ok(new ProductsResponse(200, "Product by category", product));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductsResponse(404, "Product not found", Collections.<products>emptyList()));
        }
    }

    @PostMapping
    public ResponseEntity<ProductsResponse> createProduct(@RequestBody productsDto request) {
        try {
            products product = productsService.createProduct(request);
            return ResponseEntity
                    .ok(new ProductsResponse(200, "Product created successfully", Collections.singletonList(product)));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductsResponse(500, "Internal Server Error", Collections.<products>emptyList()));
        }
    }

    @PutMapping("{productId}")
    public ResponseEntity<ProductsResponse> updateProduct(@PathVariable int productId,
            @RequestBody productsDto request) {
        try {
            products updatedProduct = productsService.updateProduct(productId, request);
            return ResponseEntity.ok(new ProductsResponse(200, "Product updated successfully",
                    Collections.singletonList(updatedProduct)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductsResponse(404, "Product not found", Collections.<products>emptyList()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductsResponse(500, "Internal Server Error", Collections.<products>emptyList()));
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<ProductsResponse> deleteProduct(@PathVariable int productId) {
        try {
            productsService.deleteProduct(productId);
            return ResponseEntity
                    .ok(new ProductsResponse(200, "Product deleted successfully", Collections.<products>emptyList()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductsResponse(404, "Product not found", Collections.<products>emptyList()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ProductsResponse(500, "Internal Server Error", Collections.<products>emptyList()));
        }
    }

}
