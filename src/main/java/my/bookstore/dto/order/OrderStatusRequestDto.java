package my.bookstore.dto.order;

import lombok.Data;
import my.bookstore.lib.OrderStatus;

@Data
public class OrderStatusRequestDto {
    @OrderStatus
    private String status;
}
