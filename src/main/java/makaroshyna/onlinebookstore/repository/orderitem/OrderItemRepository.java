package makaroshyna.onlinebookstore.repository.orderitem;

import java.util.List;
import java.util.Optional;
import makaroshyna.onlinebookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    Optional<OrderItem> findByIdAndOrderId(Long itemId, Long orderId);
}
