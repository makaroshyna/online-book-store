package makaroshyna.onlinebookstore.repository;

import java.util.List;
import makaroshyna.onlinebookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
