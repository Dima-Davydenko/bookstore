package my.bookstore.repository.cart;

import my.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByIdAndShoppingCartId(Long itemId, Long shoppingCartId);
}
