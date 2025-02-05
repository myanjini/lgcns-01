package board.dto;

import lombok.Data;

@Data
public class BookListResponse {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private String isbn;
}
