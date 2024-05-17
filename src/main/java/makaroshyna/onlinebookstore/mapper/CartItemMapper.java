package makaroshyna.onlinebookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    CartItem toModel(CreateCartItemRequestDto cartItemRequestDto);

    @Named("setCartItems")
    default Set<CartItemResponseDto> setCartItems(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
