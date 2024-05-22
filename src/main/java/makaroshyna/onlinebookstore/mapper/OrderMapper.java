package makaroshyna.onlinebookstore.mapper;

import static makaroshyna.onlinebookstore.model.Order.Status.PENDING;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.order.CreateOrderRequestDto;
import makaroshyna.onlinebookstore.dto.order.OrderResponseDto;
import makaroshyna.onlinebookstore.model.Order;
import makaroshyna.onlinebookstore.model.OrderItem;
import makaroshyna.onlinebookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto toDto(Order order);

    default Order toModel(CreateOrderRequestDto requestDto, User user, Set<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(PENDING);
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setTotalPrice(orderItems.stream()
                .map(item -> item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setOrderItems(orderItems.stream()
                .peek(item -> item.setOrder(order))
                .collect(Collectors.toSet()));
        return order;
    }
}
