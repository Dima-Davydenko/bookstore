package my.bookstore.repository.cart;

import my.bookstore.model.ShoppingCart;
import my.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query(
            "SELECT sc FROM ShoppingCart sc "
            + "LEFT JOIN FETCH sc.cartItems ci "
            + "WHERE sc.user =:user"
    )
    ShoppingCart findByUser(User user);
}
