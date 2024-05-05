package makaroshyna.onlinebookstore.service.book;

import java.util.List;
import makaroshyna.onlinebookstore.dto.book.BookDto;
import makaroshyna.onlinebookstore.dto.book.BookSearchParametersDto;
import makaroshyna.onlinebookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto parameters, Pageable pageable);
}
