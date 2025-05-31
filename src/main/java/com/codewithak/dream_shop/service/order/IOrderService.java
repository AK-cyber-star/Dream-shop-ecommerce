package com.codewithak.dream_shop.service.order;

import com.codewithak.dream_shop.dto.OrderDto;
import com.codewithak.dream_shop.model.Order;

import java.util.List;

public interface IOrderService {

    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);


    List<OrderDto> getUserOrders(Long userId);
}
