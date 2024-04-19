package makaroshyna.onlinebookstore.service;

import java.util.List;
import makaroshyna.onlinebookstore.dto.BookDto;
import makaroshyna.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
