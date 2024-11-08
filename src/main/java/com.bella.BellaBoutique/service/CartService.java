//package com.bella.BellaBoutique.service;
//import com.bella.BellaBoutique.model.products.Product;
//import com.bella.BellaBoutique.model.shopCart.CartItem;
//import com.bella.BellaBoutique.model.shopCart.ShopCart;
//import com.bella.BellaBoutique.repository.CartRepository;
//import com.bella.BellaBoutique.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CartService {
//
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//
//    public void addProductToCart(Long productId, int quantity) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product niet gevonden"));
//
//        ShopCart.Cart cart = cartRepository.findByUserId(1L);  // Voorbeeld user ID
//        CartItem cartItem = cart.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElse(new CartItem(product, 0));
//
//        cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        cart.getItems().add(cartItem);
//        cartRepository.save(cart);
//    }
//}
//
