package makaroshyna.onlinebookstore.repository.book.specification;

import static makaroshyna.onlinebookstore.repository.book.BookParameterName.TITLE;

import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationProvider;
import makaroshyna.onlinebookstore.repository.book.BookParameterName;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public BookParameterName getKey() {
        return TITLE;
    }

    @Override
    public Specification<Book> getSpecification(String paramValue) {
        return (root, query, criteriaBuilder) ->
                root.get(TITLE.getName()).in(paramValue);
    }
}
