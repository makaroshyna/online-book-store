package makaroshyna.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import makaroshyna.onlinebookstore.dto.book.BookDto;
import makaroshyna.onlinebookstore.dto.book.CreateBookRequestDto;
import makaroshyna.onlinebookstore.mapper.BookMapper;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.book.BookRepository;
import makaroshyna.onlinebookstore.repository.book.BookSpecificationBuilder;
import makaroshyna.onlinebookstore.service.book.BookService;
import makaroshyna.onlinebookstore.service.book.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper, bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Verify save() method works")
    public void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test Title");

        Book book = new Book();
        book.setTitle("Test Title");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Title");
        bookDto.setCategoryIds(Set.of());

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);

        assertNotNull(result);
        assertEquals(result.getTitle(), requestDto.getTitle());
    }

    @Test
    @DisplayName("Verify getAll() method works")
    public void getAll_ValidPageable_ReturnsBookDto() {
        when(bookRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Book())));
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Title");
        when(bookMapper.toDto(any())).thenReturn(bookDto);

        List<BookDto> result = bookService.getAll(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Verify getById() method works")
    public void getById_ValidId_ReturnsBookDto() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getById(1L);
        assertNotNull(result);
        assertEquals(result.getTitle(), bookDto.getTitle());
    }

    @Test
    @DisplayName("Verify updateById() method works")
    public void updateById_ValidData_ReturnsBookDto() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test Title");

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");

        when(bookMapper.toModel(requestDto)).thenReturn(book);

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Test Title");

        when(bookMapper.toDto(any())).thenReturn(bookDto);

        BookDto result = bookService.updateById(1L, requestDto);
        assertNotNull(result);
        assertEquals(result.getTitle(), bookDto.getTitle());
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    public void deleteById_ValidId_Success() {
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
