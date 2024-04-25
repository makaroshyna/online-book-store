package makaroshyna.onlinebookstore.repository.book.specification;

import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationProvider;
import makaroshyna.onlinebookstore.repository.book.BookParameterName;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return BookParameterName.AUTHOR.getName();
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) ->
                root.get(BookParameterName.AUTHOR.getName()).in(param);
    }
}
