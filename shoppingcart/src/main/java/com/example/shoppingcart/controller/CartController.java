
package com.example.shoppingcart.controller;

import com.example.shoppingcart.model.*;
import com.example.shoppingcart.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public CartController(CartRepository cartRepo,
                          ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    // READ cart and calculate total
    @GetMapping
    public Map<String, Object> getCart() {
        List<CartItem> items = cartRepo.findAll();
        double total = 0;

        for (CartItem i : items) {
            total += i.price * i.quantity;
        }

        return Map.of("items", items, "total", total);
    }

    // CREATE cart item or increase quantity
    @PostMapping("/add/{id}")
    public String add(@PathVariable String id) {
        Product p = productRepo.findById(id).orElse(null);

        if (p == null) return "Product not found";

        CartItem item = cartRepo.findByProductId(id)
            .orElse(new CartItem(p.id, p.name, p.price, 0));

        item.quantity++;
        cartRepo.save(item);

        return "Added";
    }

    // UPDATE quantity
    @PutMapping("/{id}")
    public String update(@PathVariable String id,
                         @RequestParam int quantity) {
        CartItem item = cartRepo.findById(id).orElse(null);

        if (item == null) return "Item not found";

        if (quantity <= 0) {
            cartRepo.delete(item);
            return "Removed";
        }

        item.quantity = quantity;
        cartRepo.save(item);

        return "Updated";
    }
    @PutMapping("/{id}")
    public String update(@PathVariable String id,
                        @RequestBody CartItem data) {

        CartItem item = cartRepo.findById(id).orElse(null);

        if (item == null) return "Item not found";

        if (data.quantity <= 0) {
            cartRepo.delete(item);
            return "Removed";
        }

        item.quantity = data.quantity;
        cartRepo.save(item);

        return "Updated";
    }

    // DELETE cart item
    @DeleteMapping("/{id}")
    public String remove(@PathVariable String id) {
        cartRepo.deleteById(id);
        return "Removed";
    }
}
