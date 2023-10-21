package my.bookstore.repository.order;

import java.util.List;
import my.bookstore.model.Order;
import my.bookstore.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi "
            + "JOIN FETCH o.user WHERE o.user = :user")
    List<Order> getAllByUser(Pageable pageable, User user);
}
