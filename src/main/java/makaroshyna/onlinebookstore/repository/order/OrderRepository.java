package makaroshyna.onlinebookstore.repository.order;

import makaroshyna.onlinebookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
