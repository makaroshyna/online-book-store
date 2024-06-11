package makaroshyna.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
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
    private CreateBookRequestDto requestDto;
    private Book book;
    private BookDto responseDto;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, bookMapper, bookSpecificationBuilder);

        requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test Title");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Title");

        responseDto = new BookDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Title");
    }

    @Test
    @DisplayName("Verify save() method works")
    public void save_ValidCreateBookRequestDto_ReturnsBookDto() {
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        BookDto result = bookService.save(requestDto);
        assertNotNull(result);
        assertEquals(result.getTitle(), requestDto.getTitle());
    }

    @Test
    @DisplayName("Verify getAll() method works")
    public void getAll_ValidPageable_ReturnsBookDto() {
        when(bookRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Book())));
        when(bookMapper.toDto(any())).thenReturn(responseDto);

        List<BookDto> result = bookService.getAll(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    @DisplayName("Verify getById() method works")
    public void getById_ValidId_ReturnsBookDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        BookDto result = bookService.getById(1L);
        assertNotNull(result);
        assertEquals(result.getTitle(), responseDto.getTitle());
    }

    @Test
    @DisplayName("Verify updateById() method works")
    public void updateById_ValidData_ReturnsBookDto() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookMapper.toDto(any())).thenReturn(responseDto);

        BookDto result = bookService.updateById(1L, requestDto);
        assertNotNull(result);
        assertEquals(result.getTitle(), responseDto.getTitle());
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    public void deleteById_ValidId_Success() {
        bookService.deleteById(1L);
        verify(bookRepository).deleteById(1L);
    }
}
