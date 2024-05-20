package makaroshyna.onlinebookstore.service.order;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.order.CreateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.order.OrderResponseDto;
import makaroshyna.onlinebookstore.dto.order.UpdateOrderRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.OrderMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.order.OrderRepository;
import makaroshyna.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import makaroshyna.onlinebookstore.service.orderitem.OrderItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto saveOrder(CreateOrderRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                                "Can't find shopping cart for user " + user.getId()));
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        if (cartItems.isEmpty()) {
            throw new EntityNotFoundException("Can't find cart items for user " + user.getId());
        }

        shoppingCartRepository.deleteById(shoppingCart.getId());
        Order order = orderMapper.toModel(requestDto, user,
                orderItemService.toOrderItem(cartItems));

        Order savedOrder = orderRepository.save(order);
        orderItemService.saveOrderItems(savedOrder.getOrderItems());

        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getAllOrders(Pageable pageable, User user) {
        List<Order> ordersByUser = orderRepository.findAllByUser(pageable, user);
        return ordersByUser.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public void updateOrder(UpdateOrderRequestDto requestDto, Long id) {
        Order order = getOrderById(id);
        order.setStatus(requestDto.getStatus());
        orderRepository.save(order);
    }

    private Order getOrderById(Long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id " + id));
    }
}
