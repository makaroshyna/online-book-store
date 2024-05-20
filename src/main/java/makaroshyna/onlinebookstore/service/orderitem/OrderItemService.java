package makaroshyna.onlinebookstore.service.orderitem;

import java.util.Set;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.OrderItem;

public interface OrderItemService {
    Set<OrderItem> toOrderItem(Set<CartItem> cartItems);

    void saveOrderItems(Set<OrderItem> orderItem);
}
