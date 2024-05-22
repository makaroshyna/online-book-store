package makaroshyna.onlinebookstore.repository.shoppingcart;

import java.util.Optional;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = "cartItems.book")
    Optional<ShoppingCart> findByUserId(Long userId);
}
