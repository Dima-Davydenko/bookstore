package my.bookstore.lib;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import my.bookstore.model.Order;

public class OrderStatusValidator implements ConstraintValidator<OrderStatus, String> {
    private static final Order.Status[] availableStatuses = Order.Status.values();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> statuses = Arrays.stream(availableStatuses)
                .map(Enum::toString)
                .toList();
        return value != null && statuses.contains(value);
    }
}
