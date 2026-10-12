
package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.Product;
import com.example.shoppingcart.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Product add(@RequestBody Product p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable String id,
                          @RequestBody Product p) {
        Product old = repo.findById(id).orElse(null);

        if (old == null) return null;

        old.name = p.name;
        old.price = p.price;

        return repo.save(old);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repo.deleteById(id);
    }
    @GetMapping("/{id}")
    public Product getOne(@PathVariable String id) {
        return repo.findById(id).orElse(null);
    }
}
