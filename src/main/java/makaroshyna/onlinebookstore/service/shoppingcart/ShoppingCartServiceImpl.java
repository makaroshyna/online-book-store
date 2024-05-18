package makaroshyna.onlinebookstore.service.shoppingcart;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CartItemResponseDto;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.shoppingcart.ShoppingCartResponseDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.CartItemMapper;
import makaroshyna.onlinebookstore.mapper.ShoppingCartMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.model.User;
import makaroshyna.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import makaroshyna.onlinebookstore.service.cartitem.CartItemService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartResponseDto getByUserId(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartByUserId(userId));
    }

    @Override
    @Transactional
    public CartItemResponseDto addToCart(CreateCartItemRequestDto requestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByUserId(user.getId())
                .orElseGet(() -> shoppingCartRepository.save(createShoppingCart(user)));
        CartItem cartItem = cartItemService.save(requestDto, shoppingCart);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCart(
            UpdateCartItemRequestDto requestDto,
            Long cartItemId,
            User user) {

        ShoppingCart shoppingCart = getShoppingCartByUserId(user.getId());
        CartItem cartItem = cartItemService.update(cartItemId, requestDto);
        shoppingCartRepository.save(shoppingCart);

        return cartItemMapper.toDto(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId, User user) {
        ShoppingCart shoppingCart = getShoppingCartByUserId(user.getId());
        cartItemService.delete(cartItemId);
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElseThrow(() ->
                new EntityNotFoundException("Can't find shopping cart by user id: " + userId));
    }

    private ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setCartItems(new HashSet<>());

        return shoppingCart;
    }
}
