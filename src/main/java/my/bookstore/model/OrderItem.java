package my.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data()
@Entity
@Table(name = "order_items")
public class OrderItem implements Comparable<OrderItem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Override
    public int compareTo(OrderItem item) {
        return id.compareTo(item.getId());
    }
}
