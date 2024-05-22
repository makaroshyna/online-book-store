package makaroshyna.onlinebookstore.service.cartitem;

import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;

public interface CartItemService {
    CartItem addToCart(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart);

    CartItem update(Long id, UpdateCartItemRequestDto requestDto);

    void delete(Long id);
}
