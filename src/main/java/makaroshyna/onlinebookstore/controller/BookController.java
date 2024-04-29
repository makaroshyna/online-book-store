package makaroshyna.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.BookDto;
import makaroshyna.onlinebookstore.dto.BookSearchParametersDto;
import makaroshyna.onlinebookstore.dto.CreateBookRequestDto;
import makaroshyna.onlinebookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books",
            description = "Get a list of all available books")
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID",
            description = "Get a book by ID, if there is one")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Get a list of books by search parameters",
            description = "Get a list of books by search parameters title, author and ISBN")
    public List<BookDto> searchBooks(BookSearchParametersDto searchParameters, Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new book",
            description = "Create a new book with generated ID")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book by ID",
            description = "Get and update a book by ID, if there is one")
    public BookDto updateBookById(@PathVariable Long id,
                                  @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.updateById(id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by ID",
            description = "Delete a book by ID, if there is one")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
