package com.project.carstore.product;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false,length = 1000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false,length = 15)
    private String colour;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String category;

    public Product() {
    }

    public Product( String name, Double price, String description, String imageUrl, String colour, Integer quantity, String category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colour = colour;
        this.quantity = quantity;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
