package com.bella.BellaBoutique.model.cart;

import com.bella.BellaBoutique.model.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isEmpty = true;

    private int productQuantity;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public void addCartItem(CartItem item) {
        cartItems.add(item);
        item.setShoppingCart(this);
        updateCartStatus();
    }

    public void removeCartItem(CartItem item) {
        cartItems.remove(item);
        item.setShoppingCart(null);
        updateCartStatus();
    }

    private void updateCartStatus() {
        this.isEmpty = cartItems.isEmpty();
        this.productQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}