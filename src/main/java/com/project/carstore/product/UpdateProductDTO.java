package com.project.carstore.product;

public class UpdateProductDTO {
    private Long productId;
    private Double price;
    private String description;
    private String imageUrl;
    private String colour;
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public UpdateProductDTO(Long productId, Double price, String description, String imageUrl, String colour, Integer quantity) {
        this.productId = productId;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colour = colour;
        this.quantity = quantity;
    }
}
