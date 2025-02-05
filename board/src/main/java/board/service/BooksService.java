package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BooksEntity;

public interface BooksService {
    List<BooksEntity> selectBooks();
    BooksEntity selectBook(int bookId);
    void insertBook(BooksEntity entity, MultipartHttpServletRequest request);
    void updateBook(BooksEntity entity);
    void deleteBook(int bookId);
}
