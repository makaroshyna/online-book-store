package makaroshyna.onlinebookstore.repository.book;

import static makaroshyna.onlinebookstore.repository.book.BookParameterName.AUTHOR;
import static makaroshyna.onlinebookstore.repository.book.BookParameterName.ISBN;
import static makaroshyna.onlinebookstore.repository.book.BookParameterName.TITLE;

import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.book.BookSearchParametersDto;
import makaroshyna.onlinebookstore.model.Book;
import makaroshyna.onlinebookstore.repository.SpecificationBuilder;
import makaroshyna.onlinebookstore.repository.SpecificationProviderManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        return Specification.allOf(
                buildSpecification(AUTHOR, searchParameters.author()),
                buildSpecification(ISBN, searchParameters.isbn()),
                buildSpecification(TITLE, searchParameters.title()));
    }

    private Specification<Book> buildSpecification(BookParameterName paramName, String paramValue) {
        if (!StringUtils.isBlank(paramValue)) {
            return bookSpecificationProviderManager
                    .getSpecificationProvider(paramName)
                    .getSpecification(paramValue);
        }

        return null;
    }
}
