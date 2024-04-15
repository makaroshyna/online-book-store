package makaroshyna.onlinebookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.BookRepository;
import makaroshyna.onlinebookstore.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
