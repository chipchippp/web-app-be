package com.example.webapp.service.impl;



import com.example.webapp.dto.OrderDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> findByUserId(Long userId);
    Order createOrder(OrderDTO orderRequest) throws Exception;
    Order getOrderById(Long id) throws DataNotFound;
    Order updateOrder(Long id, OrderDTO orderRequest) throws DataNotFound;
    void deleteOrder(Long id);
}
