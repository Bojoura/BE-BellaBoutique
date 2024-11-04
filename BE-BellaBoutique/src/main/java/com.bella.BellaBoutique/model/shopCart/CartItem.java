//package com.bella.BellaBoutique.model.shopCart;
//import com.bella.BellaBoutique.model.products.Product;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    private Product product;
//
//    private int quantity;
//
//    public CartItem(Product product, int quantity) {
//        this.product = product;
//        this.quantity = quantity;
//    }
//}
