package makaroshyna.onlinebookstore.mapper;

import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = CartItemMapper.class)
public interface ShoppingCartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "cartItems", source = "cartItems", qualifiedByName = "setCartItems")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
