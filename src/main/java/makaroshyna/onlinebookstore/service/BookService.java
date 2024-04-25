package makaroshyna.onlinebookstore.service;

import java.util.List;
import makaroshyna.onlinebookstore.dto.BookDto;
import makaroshyna.onlinebookstore.dto.BookSearchParametersDto;
import makaroshyna.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto parameters);
}
