package my.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "order_date",nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems;

    public enum Status {
        PENDING,
        IN_PROCESSING,
        SHIPPED,
        DELIVERED,
        COMPLETED,
        CANCELLED
    }
}
