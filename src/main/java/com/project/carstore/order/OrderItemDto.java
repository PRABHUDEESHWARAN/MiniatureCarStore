package com.project.carstore.order;

import com.project.carstore.product.Product;

public class OrderItemDto {

        private Product product;
        private int quantity;



        public OrderItemDto() {
        }

        public OrderItemDto(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }


}
