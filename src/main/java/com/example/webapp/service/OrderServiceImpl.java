package com.example.webapp.service;

import com.example.webapp.dto.OrderDTO;
import com.example.webapp.exceptions.DataNotFound;
import com.example.webapp.models.Order;
import com.example.webapp.models.OrderStatus;
import com.example.webapp.models.User;
import com.example.webapp.repository.OrderRepository;
import com.example.webapp.repository.UserRepository;
import com.example.webapp.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<Order> getAllOrder(Long userId) {
        return List.of();
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) throws DataNotFound {
//        User có tồn tại không
        Order order = new Order();
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFound("User not found with id: " + orderDTO.getUserId()));
//         convert orderDTO to order
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
//        ktra shipping date phai >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFound("Date must be greater than or equal to today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderRequest) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
