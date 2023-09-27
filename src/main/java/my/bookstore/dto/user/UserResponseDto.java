package my.bookstore.dto.user;

import java.util.Set;
import lombok.Data;
import my.bookstore.model.Role;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles;
}
