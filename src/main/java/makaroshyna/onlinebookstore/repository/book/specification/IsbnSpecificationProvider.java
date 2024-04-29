package makaroshyna.onlinebookstore.repository.book.specification;

import static makaroshyna.onlinebookstore.repository.book.BookParameterName.ISBN;

import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationProvider;
import makaroshyna.onlinebookstore.repository.book.BookParameterName;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public BookParameterName getKey() {
        return ISBN;
    }

    @Override
    public Specification<Book> getSpecification(String paramValue) {
        return (root, query, criteriaBuilder) ->
                root.get(ISBN.getName()).in(paramValue);
    }
}
