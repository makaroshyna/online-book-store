package makaroshyna.onlinebookstore.service.cartitem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.cartitem.CreateCartItemRequestDto;
import makaroshyna.onlinebookstore.dto.cartitem.UpdateCartItemRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.CartItemMapper;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.model.CartItem;
import makaroshyna.onlinebookstore.model.ShoppingCart;
import makaroshyna.onlinebookstore.repository.book.BookRepository;
import makaroshyna.onlinebookstore.repository.cartitem.CartItemRepository;
import makaroshyna.onlinebookstore.service.book.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public CartItem addToCart(CreateCartItemRequestDto requestDto, ShoppingCart shoppingCart) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find a book with id " + requestDto.getBookId()));

        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);

        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public CartItem update(Long id, UpdateCartItemRequestDto requestDto) {
        CartItem cartItem = getCartItemById(id);
        cartItem.setQuantity(requestDto.quantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getCartItemById(id);
        cartItemRepository.deleteById(id);
    }

    private CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException("Can't find a cart item with id " + id));
    }
}
