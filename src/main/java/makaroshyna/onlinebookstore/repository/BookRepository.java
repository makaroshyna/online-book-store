package makaroshyna.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import makaroshyna.onlinebookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
