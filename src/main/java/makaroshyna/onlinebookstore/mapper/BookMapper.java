package makaroshyna.onlinebookstore.mapper;

import makaroshyna.onlinebookstore.config.MapperConfig;
import makaroshyna.onlinebookstore.dto.BookDto;
import makaroshyna.onlinebookstore.dto.CreateBookRequestDto;
import makaroshyna.onlinebookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
