
package com.example.shoppingcart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    public String id;
    public String name;
    public double price;

    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
