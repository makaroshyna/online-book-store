package makaroshyna.onlinebookstore.service.orderitem;

import java.util.List;
import java.util.Set;
import makaroshyna.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.OrderItem;

public interface OrderItemService {
    Set<OrderItem> toOrderItem(Set<CartItem> cartItems);

    void saveOrderItems(Set<OrderItem> orderItem);

    List<OrderItemResponseDto> getAllOrderItems(Order order);
}
