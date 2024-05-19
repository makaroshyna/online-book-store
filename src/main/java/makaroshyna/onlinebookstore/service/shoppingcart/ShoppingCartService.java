package makaroshyna.onlinebookstore.service.shoppingcart;

import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(User user);

    CartItemResponseDto addToCart(CreateCartItemRequestDto requestDto, User user);

    CartItemResponseDto updateCart(UpdateCartItemRequestDto requestDto, Long cartItemId, User user);

    void deleteCartItem(Long cartItemId, User user);
}
