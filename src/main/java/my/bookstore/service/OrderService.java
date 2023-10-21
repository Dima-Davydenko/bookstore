package my.bookstore.service;

import java.util.List;
import my.bookstore.dto.order.OrderDto;
import my.bookstore.dto.order.OrderItemDto;
import my.bookstore.dto.order.OrderRequestDto;
import my.bookstore.dto.order.OrderStatusRequestDto;
import my.bookstore.model.User;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(User user, OrderRequestDto request);

    List<OrderDto> getOrderHistory(Pageable pageable, User user);

    List<OrderItemDto> getAllItemsFromOrder(User user, Long orderId, Pageable pageable);

    OrderItemDto getOrderItemFromOrder(User user, Long orderId, Long itemId, Pageable pageable);

    void updateStatus(Long orderId, OrderStatusRequestDto status);
}
