package com.example.webapp.service.impl;

import com.example.webapp.dto.OrderDetailDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.OrderDetails;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetails> findByOrderId(Long orderId);
    OrderDetails getOrderDetailById(Long id) throws DataNotFound;
    OrderDetails createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFound;
    OrderDetails updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFound;
    void deleteOrderDetail(Long id);
}
