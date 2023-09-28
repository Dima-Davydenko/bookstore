package my.bookstore.repository.user;

import java.util.Optional;
import my.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.roles")
    Optional<User> findByEmail(String email);
}
