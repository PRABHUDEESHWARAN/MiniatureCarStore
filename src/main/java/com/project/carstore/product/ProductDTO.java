package com.project.carstore.product;

public class ProductDTO {
<<<<<<< HEAD
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private String colour;
    private Integer quantity;
=======
   
    private String Name;
    private Double Price;
    private String Description;
    private String ImageUrl;
    private String Colour;
    private Integer Quantity;
>>>>>>> b1158df34438c58e145bd05676dbe8fe3e09ef15

    public ProductDTO() {
    }

<<<<<<< HEAD
    public ProductDTO(String name, Double price, String description, String imageUrl, String colour, Integer quantity) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.colour = colour;
        this.quantity = quantity;
=======
    public ProductDTO( String name, Double price, String description, String imageUrl, String colour, Integer quantity) {
        
        Name = name;
        Price = price;
        Description = description;
        ImageUrl = imageUrl;
        Colour = colour;
        Quantity = quantity;
>>>>>>> b1158df34438c58e145bd05676dbe8fe3e09ef15
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
