package my.bookstore.mapper;

import my.bookstore.config.MapperConfig;
import my.bookstore.dto.order.OrderDto;
import my.bookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "orderItems")
    @Mapping(source = "user.id", target = "userId")
    OrderDto toDto(Order order);
}
