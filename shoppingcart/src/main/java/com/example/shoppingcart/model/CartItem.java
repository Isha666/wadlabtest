
package com.example.shoppingcart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class CartItem {

    @Id
    public String id;
    public String productId;
    public String name;
    public double price;
    public int quantity;

    public CartItem() {}

    public CartItem(String productId, String name,
                    double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
