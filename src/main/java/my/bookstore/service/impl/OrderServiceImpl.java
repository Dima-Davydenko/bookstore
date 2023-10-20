package my.bookstore.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.cart.CartItemDto;
import my.bookstore.dto.order.OrderDto;
import my.bookstore.dto.order.OrderItemDto;
import my.bookstore.dto.order.OrderRequestDto;
import my.bookstore.dto.order.OrderStatusRequestDto;
import my.bookstore.exception.EntityNotFoundException;
import my.bookstore.mapper.OrderItemMapper;
import my.bookstore.mapper.OrderMapper;
import my.bookstore.model.Book;
import my.bookstore.model.Order;
import my.bookstore.model.OrderItem;
import my.bookstore.model.ShoppingCart;
import my.bookstore.model.User;
import my.bookstore.repository.book.BookRepository;
import my.bookstore.repository.cart.CartItemRepository;
import my.bookstore.repository.cart.ShoppingCartRepository;
import my.bookstore.repository.order.OrderItemRepository;
import my.bookstore.repository.order.OrderRepository;
import my.bookstore.service.OrderService;
import my.bookstore.service.ShoppingCartService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public OrderDto createOrder(User user, OrderRequestDto request) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(BigDecimal.ZERO);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        Order preparedOrder = orderRepository.save(order);
        setOrderItemsToOrder(user, preparedOrder);
        Order completedOrder = orderRepository.save(preparedOrder);
        clearShoppingCart(user);
        return orderMapper.toDto(completedOrder);
    }

    @Override
    public List<OrderDto> getOrderHistory(Pageable pageable, User user) {
        List<Order> usersOrdersHistory = orderRepository.getAllByUser(pageable, user);
        return usersOrdersHistory.stream().map(orderMapper::toDto).toList();
    }

    @Override
    public List<OrderItemDto> getAllItemsFromOrder(User user, Long orderId) {
        List<Order> usersOrdersHistory = orderRepository.getAllByUser(user);
        Order order = usersOrdersHistory.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no order with id " + orderId));
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemFromOrder(User user, Long orderId, Long itemId) {
        List<OrderItemDto> allItemsFromOrder = getAllItemsFromOrder(user, orderId);
        return allItemsFromOrder.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no order item with id " + itemId));
    }

    @Override
    public void updateStatus(Long orderId, OrderStatusRequestDto status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("There is no order with id " + orderId));
        order.setStatus(Order.Status.valueOf(status.getStatus()));
        orderRepository.save(order);
    }

    private void setOrderItemsToOrder(User user, Order order) {
        Set<OrderItem> orderItems = new TreeSet<>();
        Set<CartItemDto> cartItems = shoppingCartService.getShoppingCart(user).getCartItems();
        for (CartItemDto cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            Book book = bookRepository.findById(cartItem.getBookId()).orElseThrow(
                    () -> new EntityNotFoundException("There is no book with id "
                            + cartItem.getBookId()));
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(book.getPrice());
            OrderItem savedItem = orderItemRepository.save(orderItem);
            orderItems.add(savedItem);
            order.setTotal(order.getTotal().add(getOrderItemTotalPrice(orderItem)));
        }
        order.setOrderItems(orderItems);
    }

    private void clearShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
        cartItemRepository.deleteAllByShoppingCartId(shoppingCart.getId());
    }

    private BigDecimal getOrderItemTotalPrice(OrderItem orderItem) {
        BigDecimal bookPrice = orderItem.getBook().getPrice();
        return bookPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
    }
}
