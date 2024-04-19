package makaroshyna.onlinebookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.BookDto;
import makaroshyna.onlinebookstore.dto.CreateBookRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.BookMapper;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.BookRepository;
import makaroshyna.onlinebookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDto(book);
    }
}
