package makaroshyna.onlinebookstore.service.shoppingcart;

import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long userId);

    CartItemResponseDto addToCart(CreateCartItemRequestDto requestDto, Long userId);

    CartItemResponseDto updateCart(
            UpdateCartItemRequestDto requestDto, Long cartItemId, Long userId);

    void deleteCartItem(Long cartItemId, Long userId);

    void createShoppingCartForUser(User user);
}
