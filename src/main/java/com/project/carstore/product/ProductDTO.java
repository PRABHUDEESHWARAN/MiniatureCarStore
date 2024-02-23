package com.project.carstore.product;

public class ProductDTO {
   
    private String Name;
    private Double Price;
    private String Description;
    private String ImageUrl;
    private String Colour;
    private Integer Quantity;

    public ProductDTO() {
    }

    public ProductDTO( String name, Double price, String description, String imageUrl, String colour, Integer quantity) {
        
        Name = name;
        Price = price;
        Description = description;
        ImageUrl = imageUrl;
        Colour = colour;
        Quantity = quantity;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
}
