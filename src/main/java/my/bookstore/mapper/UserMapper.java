package my.bookstore.mapper;

import my.bookstore.config.MapperConfig;
import my.bookstore.dto.user.UserResponseDto;
import my.bookstore.model.Role;
import my.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class,
        imports = Role.class
)
public interface UserMapper {
    UserResponseDto toDto(User user);
}
