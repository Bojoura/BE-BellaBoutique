//package com.bella.BellaBoutique.controller;
//
//import com.bella.BellaBoutique.DTO.CartItemRequest;
//import com.bella.BellaBoutique.service.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/cart")
//@RequiredArgsConstructor
//public class CartController {
//
//    private CartService cartService;
//
//    @PostMapping
//    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest cartItemRequest) {
//        cartService.addProductToCart(cartItemRequest.getProductId(), cartItemRequest.getQuantity());
//        return ResponseEntity.ok("Product toegevoegd aan de winkelwagen");
//    }
//}
//
