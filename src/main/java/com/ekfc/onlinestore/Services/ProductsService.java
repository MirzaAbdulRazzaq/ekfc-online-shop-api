package com.ekfc.onlinestore.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ekfc.onlinestore.InterfaceRepos.productRepo;
import com.ekfc.onlinestore.Models.products.products;
import com.ekfc.onlinestore.Models.products.productsDto;

@Service
public class ProductsService {
    private final productRepo repository;

    public ProductsService(productRepo repository) {
        this.repository = repository;
    }

    public products createProduct(productsDto request) {
        products product = new products();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setRating(request.getRating());
        product.setPrice(request.getPrice());

        product = repository.save(product);

        return product;
    }

    public List<products> getAllProducts() {
        return repository.findAll();
    }

    public products getSpecificProduct(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + id));
    }

    public List<products> getProductsByCategory(String category) {
        return repository.findAllByCategory(category);
    }

    public products updateProduct(int productId, productsDto request) {
        Optional<products> optionalProduct = repository.findById(productId);

        if (optionalProduct.isPresent()) {
            products product = optionalProduct.get();
            product.setName(request.getName());
            product.setCategory(request.getCategory());
            product.setRating(request.getRating());
            product.setPrice(request.getPrice());
            product = repository.save(product);
            return product;
        } else {
            throw new NoSuchElementException("Product not found with ID: " + productId);
        }
    }

    public void deleteProduct(int productId) {
        Optional<products> optionalProduct = repository.findById(productId);

        if (optionalProduct.isPresent()) {
            repository.deleteById(productId);
        } else {
            throw new NoSuchElementException("Product not found with ID: " + productId);
        }
    }
}
