package makaroshyna.onlinebookstore.service.orderitem;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.mapper.OrderItemMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.OrderItem;
import makaroshyna.onlinebookstore.repository.orderitem.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<OrderItem> toOrderItem(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(orderItemMapper::toOrderItem)
                .collect(Collectors.toSet());
    }

    @Override
    public void saveOrderItems(Set<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
