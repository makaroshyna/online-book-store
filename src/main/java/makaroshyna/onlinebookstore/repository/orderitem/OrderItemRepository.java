package makaroshyna.onlinebookstore.repository.orderitem;

import java.util.List;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
