package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BooksEntity;
import board.repository.BooksRepository;

@Service
public class BooksServiceImpl implements BooksService {
    @Autowired
    private BooksRepository repository;
    
    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BooksEntity> selectBooks() {
        return repository.findAll();
    }

    @Override
    public BooksEntity selectBook(int bookId) {
        return repository.findById(bookId).orElse(null);
    }

    @Override
    public void insertBook(BooksEntity entity, MultipartHttpServletRequest request) {
        // TODO. 이미지 처리
        repository.save(entity);        
    }

    @Override
    public void updateBook(BooksEntity entity) {
        repository.save(entity);
    }

    @Override
    public void deleteBook(int bookId) {
        repository.deleteById(bookId);
    }
}
