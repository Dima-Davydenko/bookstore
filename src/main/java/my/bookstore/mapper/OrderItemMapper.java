package my.bookstore.mapper;

import my.bookstore.config.MapperConfig;
import my.bookstore.dto.order.OrderItemDto;
import my.bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem item);

}
