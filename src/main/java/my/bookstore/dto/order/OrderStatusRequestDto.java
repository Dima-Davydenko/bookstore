package my.bookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import my.bookstore.model.Order;

@Data
public class OrderStatusRequestDto {
    @NotNull
    private Order.Status status;
}
