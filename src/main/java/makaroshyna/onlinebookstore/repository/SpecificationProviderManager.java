package makaroshyna.onlinebookstore.repository;

import makaroshyna.onlinebookstore.repository.book.BookParameterName;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(BookParameterName key);
}
