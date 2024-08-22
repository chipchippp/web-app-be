package com.example.webapp.service;

import com.example.webapp.dto.OrderDetailDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.Order;
import com.example.webapp.models.OrderDetails;
import com.example.webapp.models.Products;
import com.example.webapp.repository.OrderDetailRepository;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.repository.ProductRepository;
import com.example.webapp.service.impl.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OderDetailService implements OrderDetailService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetails> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetails getOrderDetailById(Long id) throws DataNotFound {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFound
                        ("Order detail not found" + id));
    }

    @Override
    public OrderDetails createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFound {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFound
                        ("Order not found" + orderDetailDTO.getOrderId()));
        Products products = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFound
                        ("Product not found" + orderDetailDTO.getProductId()));
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .product(products)
                .numberOfProducts(orderDetailDTO.getNumberOfProduct())
                .totalPrice(orderDetailDTO.getTotalPrice())
                .color(orderDetailDTO.getColor())
                .price(orderDetailDTO.getPrice())
                .build();
        return orderDetailRepository.save(orderDetails);
    }

    @Override
    public OrderDetails updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFound {
        OrderDetails orderDetails = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFound
                        ("Order detail not found" + id));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFound
                        ("Order not found" + orderDetailDTO.getOrderId()));
        Products products = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFound
                        ("Product not found" + orderDetailDTO.getProductId()));
        orderDetails.setOrder(order);
        orderDetails.setProduct(products);
        orderDetails.setPrice(orderDetailDTO.getPrice());
        orderDetails.setNumberOfProducts(orderDetailDTO.getNumberOfProduct());
        orderDetails.setTotalPrice(orderDetailDTO.getTotalPrice());
        orderDetails.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(orderDetails);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
