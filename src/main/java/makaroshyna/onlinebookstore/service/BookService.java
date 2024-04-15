package makaroshyna.onlinebookstore.service;

import java.util.List;
import makaroshyna.onlinebookstore.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
