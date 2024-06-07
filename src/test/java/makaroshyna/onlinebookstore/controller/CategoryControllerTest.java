package makaroshyna.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import makaroshyna.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import makaroshyna.onlinebookstore.dto.category.CategoryResponseDto;
import makaroshyna.onlinebookstore.dto.category.CreateCategoryRequestDto;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final String CATEGORIES_URL = "/categories";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext context,
            @Autowired DataSource dataSource) {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        addThreeCategories(dataSource);
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @Test
    @WithMockUser
    @DisplayName("Retrieve all categories")
    public void getAll_GivenCategories_ReturnsAllCategories() throws Exception {
        List<CategoryResponseDto> expected = new ArrayList<>();
        expected.add(new CategoryResponseDto(1L, "Fantasy", "Fantasy Description"));
        expected.add(new CategoryResponseDto(2L, "Romance novel", "Romance novel Description"));
        expected.add(new CategoryResponseDto(3L, "Detective", "Detective Description"));

        MvcResult result = mockMvc.perform(get(CATEGORIES_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto[].class);

        assertEquals(expected.size(), actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @WithMockUser
    @DisplayName("Retrieve a category by valid ID")
    public void getCategoryById_GivenId_ReturnsCategory() throws Exception {
        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                "Fantasy",
                "Fantasy Description");

        MvcResult result = mockMvc.perform(get(CATEGORIES_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser
    @DisplayName("Get a list of books by valid category ID")
    public void getBooksByCategoryId_GivenId_ReturnsBooks(
            @Autowired DataSource dataSource) throws Exception {

        addThreeBooksAndCategories(dataSource);

        BookDtoWithoutCategoryIds fellowship = new BookDtoWithoutCategoryIds();
        fellowship.setId(1L);
        fellowship.setAuthor("J. R. R. Tolkien");
        fellowship.setTitle("The Fellowship of the Ring");
        fellowship.setIsbn("9780007136599");
        fellowship.setPrice(BigDecimal.valueOf(520.55));

        BookDtoWithoutCategoryIds towers = new BookDtoWithoutCategoryIds();
        towers.setId(2L);
        towers.setAuthor("J. R. R. Tolkien");
        towers.setTitle("The Two Towers");
        towers.setIsbn("9780007136568");
        towers.setPrice(BigDecimal.valueOf(490.95));

        List<BookDtoWithoutCategoryIds> expected = List.of(fellowship, towers);
        MvcResult result = mockMvc.perform(get(CATEGORIES_URL + "/1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDtoWithoutCategoryIds[].class);

        assertNotNull(actual);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Create a new category with valid data")
    public void createCategory_ValidCategory_ReturnsCategory() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Test Category");
        requestDto.setDescription("Test Description");

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                requestDto.getName(),
                requestDto.getDescription());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(post(CATEGORIES_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Update a category with valid ID")
    public void updateCategoryById_ValidId_ReturnsCategory() throws Exception {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated Category");
        requestDto.setDescription("Updated Description");

        CategoryResponseDto expected = new CategoryResponseDto(
                1L,
                requestDto.getName(),
                requestDto.getDescription());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(put(CATEGORIES_URL + "/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Delete existing category")
    public void deleteCategoryById_GivenId_NoContentStatus() throws Exception {
        mockMvc.perform(delete(CATEGORIES_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/teardown.sql"));
        }
    }

    @SneakyThrows
    private void addThreeCategories(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/categories/add-three-categories.sql"));
        }
    }

    @SneakyThrows
    private void addThreeBooksAndCategories(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-three-books.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books-categories/add-categories-to-books.sql"));
        }
    }
}
