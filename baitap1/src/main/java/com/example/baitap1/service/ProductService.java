package com.example.baitap1.service;

import com.example.baitap1.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private List<Product> products = new ArrayList<>();
    private long nextId = 3;

    public ProductService() {
        products.add(new Product(1L, "Laptop", 1000, "Gaming Laptop", "Dell"));
        products.add(new Product(2L, "Phone", 500, "Smartphone", "Samsung"));
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public void update(Long id, Product product) {
        Product p = findById(id);
        if (p != null) {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setDescription(product.getDescription());
            p.setManufacturer(product.getManufacturer());
        }
    }

    @Override
    public void delete(Long id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Long generateId() {
        return nextId++;
    }
}
