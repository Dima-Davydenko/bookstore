package my.bookstore.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemQuantityRequestDto {
    @NotNull
    @Min(1)
    private int quantity;
}
