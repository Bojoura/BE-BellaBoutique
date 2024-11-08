//package com.bella.BellaBoutique.model.shopCart;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.apache.catalina.User;
//
//import java.util.List;
//
//public class ShopCart {
//
//    @Entity
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public class Cart {
//
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//
//        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//        private List<CartItem> items;
//
//        @OneToOne
//        private User user;
//    }
//}
