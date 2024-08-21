package com.example.webapp.service.impl;



import com.example.webapp.dto.OrderDTO;
import com.example.webapp.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder(Long userId);

    Order createOrder(OrderDTO orderRequest) throws Exception;
    Order getOrderById(Long id);
    Order updateOrder(Long id, OrderDTO orderRequest);
    void deleteOrder(Long id);
}
