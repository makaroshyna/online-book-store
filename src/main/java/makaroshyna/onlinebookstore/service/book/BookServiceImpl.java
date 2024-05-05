package makaroshyna.onlinebookstore.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.book.BookDto;
import makaroshyna.onlinebookstore.dto.book.BookSearchParametersDto;
import makaroshyna.onlinebookstore.dto.book.CreateBookRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.BookMapper;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.book.BookRepository;
import makaroshyna.onlinebookstore.repository.book.BookSpecificationBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        if (bookRepository.existsById(id)) {
            Book book = bookMapper.toModel(requestDto);
            book.setId(id);
            return bookMapper.toDto(bookRepository.save(book));
        }
        throw new EntityNotFoundException("Can't find book by id: " + id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto parameters, Pageable pageable) {
        return bookRepository.findAll(bookSpecificationBuilder.build(parameters), pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
