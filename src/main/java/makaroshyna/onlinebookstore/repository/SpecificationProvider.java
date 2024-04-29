package makaroshyna.onlinebookstore.repository;

import makaroshyna.onlinebookstore.repository.book.BookParameterName;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    BookParameterName getKey();

    Specification<T> getSpecification(String param);
}
