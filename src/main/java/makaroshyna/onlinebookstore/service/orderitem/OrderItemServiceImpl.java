package makaroshyna.onlinebookstore.service.orderitem;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.OrderItemMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.OrderItem;
import makaroshyna.onlinebookstore.repository.orderitem.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Set<OrderItem> toOrderItem(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(orderItemMapper::toOrderItem)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void saveOrderItems(Set<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

    @Override
    @Transactional
    public List<OrderItemResponseDto> getAllOrderItems(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

        if (orderItems.isEmpty()) {
            throw new EntityNotFoundException("No order items found for order with id "
                                              + order.getId());
        }

        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }
}
