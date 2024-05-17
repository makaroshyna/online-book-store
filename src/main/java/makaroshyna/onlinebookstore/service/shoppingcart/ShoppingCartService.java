package makaroshyna.onlinebookstore.service.shoppingcart;

import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getByUserId(Long userId);
}
