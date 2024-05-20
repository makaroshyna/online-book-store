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
import makaroshyna.onlinebookstore.repository.order.OrderRepository;
import makaroshyna.onlinebookstore.repository.orderitem.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

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
    public List<OrderItemResponseDto> getAllOrderItems(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        if (orderItems.isEmpty()) {
            throw new EntityNotFoundException("No order items found for order with id "
                                              + orderId);
        }

        return orderItems.stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findByIdAndOrderId(itemId, orderId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order item with id " + itemId));

        return orderItemMapper.toDto(orderItem);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException("Can't find order with id: " + orderId));
    }
}
