package com.project.carstore.product;

public class UpdateProductDTO {
    private Long productId;
    private Double Price;
    private String Description;
    private String ImageUrl;
    private String Colour;
    private Integer Quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public UpdateProductDTO(Long productId, Double price, String description, String imageUrl, String colour, Integer quantity) {
        this.productId = productId;
        Price = price;
        Description = description;
        ImageUrl = imageUrl;
        Colour = colour;
        Quantity = quantity;
    }
}
