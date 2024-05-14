package makaroshyna.onlinebookstore.service.category;

import java.util.List;
import makaroshyna.onlinebookstore.dto.category.CategoryResponseDto;
import makaroshyna.onlinebookstore.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    List<CategoryResponseDto> findAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto updateById(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
