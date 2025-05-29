package com.codewithak.dream_shop.service.cart;

import com.codewithak.dream_shop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
