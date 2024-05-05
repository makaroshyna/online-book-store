package makaroshyna.onlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @Size(min = 13)
    @NotBlank
    private String isbn;
    @Min(value = 0)
    @NotNull
    private BigDecimal price;
    private String description;
    private String coverImage;
}
