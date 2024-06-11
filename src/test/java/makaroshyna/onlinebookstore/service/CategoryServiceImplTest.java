package makaroshyna.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import makaroshyna.onlinebookstore.dto.category.CategoryResponseDto;
import makaroshyna.onlinebookstore.dto.category.CreateCategoryRequestDto;
import makaroshyna.onlinebookstore.mapper.CategoryMapper;
import makaroshyna.onlinebookstore.model.Category;
import makaroshyna.onlinebookstore.repository.category.CategoryRepository;
import makaroshyna.onlinebookstore.service.book.BookService;
import makaroshyna.onlinebookstore.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private BookService bookService;
    private CategoryServiceImpl categoryService;
    private CategoryResponseDto responseDto;
    private CreateCategoryRequestDto requestDto;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper, bookService);

        requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Test category");
        requestDto.setDescription("Test description");

        category = new Category();
        category.setId(1L);
        category.setName("Test category");
        category.setDescription("Test description");

        responseDto = new CategoryResponseDto(
                1L,
                "Test category",
                "Test description");
    }

    @Test
    @DisplayName("Verify save() method works")
    public void save_ValidCreateCategoryRequestDto_ReturnsCategoryResponseDto() {
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto actual = categoryService.save(requestDto);
        assertNotNull(actual);
        assertEquals(responseDto, actual);
    }

    @Test
    @DisplayName("Verify findAll() method works")
    public void findAll_ValidPageable_ReturnsAllCategories() {
        when(categoryRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(new Category())));
        when(categoryMapper.toDtoList(any())).thenReturn(List.of(responseDto));

        List<CategoryResponseDto> actual = categoryService.findAll(Pageable.unpaged());
        assertNotNull(actual);
        assertEquals(actual.size(), 1);
    }

    @Test
    @DisplayName("Verify getById() method works")
    public void getById_ValidId_ReturnsCategoryResponseDto() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto actual = categoryService.getById(1L);
        assertNotNull(actual);
        assertEquals(responseDto, actual);
    }

    @Test
    @DisplayName("Verity updateById() method words")
    public void updateById_ValidId_ReturnsCategoryResponseDto() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryMapper.toDto(any())).thenReturn(responseDto);

        CategoryResponseDto actual = categoryService.updateById(1L, requestDto);
        assertNotNull(actual);
        assertEquals(responseDto, actual);
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    public void deleteById_ValidId_Success() {
        categoryService.deleteById(1L);
        verify(categoryRepository).deleteById(1L);
    }
}
