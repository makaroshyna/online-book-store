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
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import makaroshyna.onlinebookstore.dto.book.BookDto;
import makaroshyna.onlinebookstore.dto.book.CreateBookRequestDto;
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
class BookControllerTest {
    protected static MockMvc mockMvc;
    private static final String BOOKS_URL = "/books";

    @Autowired
    private ObjectMapper objectMapper;
    private BookDto fellowship;
    private BookDto towers;
    private BookDto hound;
    private CreateBookRequestDto requestDto;

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
        addThreeBooks(dataSource);

        fellowship = new BookDto();
        fellowship.setId(1L);
        fellowship.setTitle("The Fellowship of the Ring");
        fellowship.setAuthor("J. R. R. Tolkien");
        fellowship.setIsbn("9780007136599");
        fellowship.setPrice(BigDecimal.valueOf(520.55));
        fellowship.setCategoryIds(Set.of());

        towers = new BookDto();
        towers.setId(2L);
        towers.setTitle("The Two Towers");
        towers.setAuthor("J. R. R. Tolkien");
        towers.setIsbn("9780007136568");
        towers.setPrice(BigDecimal.valueOf(490.95));
        towers.setCategoryIds(Set.of());

        hound = new BookDto();
        hound.setId(3L);
        hound.setTitle("The Hound of the Baskervilles");
        hound.setAuthor("Sir Arthur Conan Doyle");
        hound.setIsbn("0-14-020823-7");
        hound.setPrice(BigDecimal.valueOf(179.95));
        hound.setCategoryIds(Set.of());

        requestDto = new CreateBookRequestDto();
        requestDto.setTitle("1984");
        requestDto.setAuthor("George Orwell");
        requestDto.setIsbn("9780451524935");
        requestDto.setPrice(BigDecimal.valueOf(372.55));
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @Test
    @WithMockUser
    @DisplayName("Get a list of all existing books")
    public void getAll_GivenBooks_ReturnsAllBooks() throws Exception {
        List<BookDto> expected = new ArrayList<>();
        expected.add(fellowship);
        expected.add(towers);
        expected.add(hound);

        MvcResult result = mockMvc.perform(get(BOOKS_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto[].class);

        assertNotNull(actual);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    @WithMockUser
    @DisplayName("Get a book by valid ID")
    public void getBookById_GivenId_ReturnsBook() throws Exception {
        MvcResult result = mockMvc.perform(get(BOOKS_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class);

        assertNotNull(actual);
        assertEquals(fellowship, actual);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Create a new book with valid data")
    public void createBook_GivenValidData_ReturnsBook() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(4L);
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setIsbn(requestDto.getIsbn());
        expected.setPrice(requestDto.getPrice());
        expected.setCategoryIds(Set.of());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(post(BOOKS_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class);

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Update existing book")
    public void updateBookById_GivenValidData_ReturnsBook() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setIsbn(requestDto.getIsbn());
        expected.setPrice(requestDto.getPrice());
        expected.setCategoryIds(Set.of());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(put(BOOKS_URL + "/1")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                BookDto.class);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Get not found status when updating book with non-existing ID")
    public void updateBookById_GivenInvalidId_NotFoundStatus() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(put(BOOKS_URL + "/100")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Delete existing book")
    public void deleteBookById_GivenValidId_NoContentStatus() throws Exception {
        mockMvc.perform(delete(BOOKS_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Get bad request status when deleting book with non-existing ID")
    public void deleteBookById_GivenInvalidId_BadRequestStatus() throws Exception {
        mockMvc.perform(put(BOOKS_URL + "/100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
    private void addThreeBooks(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/books/add-three-books.sql"));
        }
    }
}
