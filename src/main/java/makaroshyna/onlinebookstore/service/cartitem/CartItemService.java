package makaroshyna.onlinebookstore.service.cartitem;

import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;

public interface CartItemService {
    CartItem save(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart);
}
