package makaroshyna.onlinebookstore.service.cartitem;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.CartItemMapper;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.repository.cartitem.CartItemRepository;
import makaroshyna.onlinebookstore.service.book.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;

    @Override
    @Transactional
    public CartItem save(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        String title = bookService.getById(requestDto.getBookId()).getTitle();
        cartItem.getBook().setTitle(title);
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem update(Long id, UpdateCartItemRequestDto requestDto) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(requestDto.quantity());
            return cartItemRepository.save(cartItem);
        }

        throw new EntityNotFoundException("CartItem with id " + id + " not found");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
