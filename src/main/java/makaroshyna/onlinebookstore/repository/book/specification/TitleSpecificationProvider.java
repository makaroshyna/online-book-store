package makaroshyna.onlinebookstore.repository.book.specification;

import static makaroshyna.onlinebookstore.repository.book.BookParameterName.TITLE;

import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return TITLE.getName();
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) ->
                root.get(TITLE.getName()).in(param);
    }
}
