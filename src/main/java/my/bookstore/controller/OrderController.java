package my.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.order.OrderDto;
import my.bookstore.dto.order.OrderItemDto;
import my.bookstore.dto.order.OrderRequestDto;
import my.bookstore.dto.order.OrderStatusRequestDto;
import my.bookstore.model.User;
import my.bookstore.service.OrderService;
import my.bookstore.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing users orders")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Create an order.",
            description = "Forming an order from items in users shopping cart.")
    public OrderDto createOrder(@RequestBody OrderRequestDto request, Authentication auth) {
        User user = userService.getAuthenticatedUser(auth);
        return orderService.createOrder(user, request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get user's orders history.", description = "Get all user's orders.")
    public List<OrderDto> getOrdersHistory(Pageable pageable, Authentication auth) {
        User user = userService.getAuthenticatedUser(auth);
        return orderService.getOrderHistory(pageable, user);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get order items.",
            description = "Get all order items from user's specific order.")
    public List<OrderItemDto> getAllItemsFromOrder(@PathVariable Long orderId,
                                                   Authentication auth,
                                                   Pageable pageable) {
        User user = userService.getAuthenticatedUser(auth);
        return orderService.getAllItemsFromOrder(user, orderId, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get order item.",
            description = "Get specific order item from user's specific order.")
    public OrderItemDto getOrderItemFromOrder(@PathVariable Long orderId,
                                              @PathVariable Long itemId,
                                              Authentication auth,
                                              Pageable pageable) {
        User user = userService.getAuthenticatedUser(auth);
        return orderService.getOrderItemFromOrder(user, orderId, itemId, pageable);
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update order status.", description = "Updating order Status by admin.")
    public void updateOrderStatus(@PathVariable Long orderId,
                                  @Valid @RequestBody OrderStatusRequestDto status) {
        orderService.updateStatus(orderId, status);
    }
}
