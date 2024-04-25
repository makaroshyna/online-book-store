package makaroshyna.onlinebookstore.repository.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookParameterName {
    AUTHOR("author"),
    TITLE("title"),
    ISBN("isbn");

    private final String name;
}
