package makaroshyna.onlinebookstore.repository.book.specification;

import static makaroshyna.onlinebookstore.repository.book.BookParameterName.ISBN;

import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return ISBN.getName();
    }

    @Override
    public Specification<Book> getSpecification(String paramValue) {
        return (root, query, criteriaBuilder) ->
                root.get(ISBN.getName()).in(paramValue);
    }
}
