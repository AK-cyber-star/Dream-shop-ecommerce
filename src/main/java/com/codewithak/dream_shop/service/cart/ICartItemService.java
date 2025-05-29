package com.codewithak.dream_shop.service.cart;


import com.codewithak.dream_shop.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long id, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);


    CartItem getCartItem(Long cartId, Long productId);
}
