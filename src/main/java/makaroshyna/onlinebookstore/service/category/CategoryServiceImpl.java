package makaroshyna.onlinebookstore.service.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import makaroshyna.onlinebookstore.dto.category.CategoryResponseDto;
import makaroshyna.onlinebookstore.dto.category.CreateCategoryRequestDto;
import makaroshyna.onlinebookstore.exception.EntityNotFoundException;
import makaroshyna.onlinebookstore.mapper.CategoryMapper;
import makaroshyna.onlinebookstore.model.Category;
import makaroshyna.onlinebookstore.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto updateById(Long id, CreateCategoryRequestDto requestDto) {
        if (categoryRepository.existsById(id)) {
            Category category = categoryMapper.toModel(requestDto);
            category.setId(id);
            return categoryMapper.toDto(categoryRepository.save(category));
        }
        throw new EntityNotFoundException("Category with id " + id + " not found");
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
