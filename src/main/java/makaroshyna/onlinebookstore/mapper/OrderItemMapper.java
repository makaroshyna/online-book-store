package makaroshyna.onlinebookstore.mapper;

import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    default OrderItem toOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        return orderItem;
    }
}
