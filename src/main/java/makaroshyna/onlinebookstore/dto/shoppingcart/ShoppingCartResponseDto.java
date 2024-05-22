package makaroshyna.onlinebookstore.dto.shoppingcart;

import java.util.Set;
import lombok.Data;
import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
