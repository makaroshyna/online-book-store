package makaroshyna.onlinebookstore.repository.order;

import java.util.List;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "orderItems.book")
    List<Order> findAllByUser(Pageable pageable, User user);
}
