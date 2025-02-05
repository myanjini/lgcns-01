package board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import board.dto.BookListResponse;
import board.dto.BookUpdateRequest;
import board.entity.BooksEntity;
import board.service.BooksService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/book")
public class BooksController {
    
    @Autowired
    private BooksService service;
    
    // 목록 조회
    @GetMapping("/")
    public ResponseEntity<Object> selectBooks() throws Exception {
        List<BookListResponse> results = new ArrayList<>();
        
        try {
            List<BooksEntity> bookList = service.selectBooks();
            bookList.forEach(entity -> results.add(new ModelMapper().map(entity, BookListResponse.class)));
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("도서 목록 조회 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // 저장 처리
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> insertBook(@RequestParam("book") String bookInfo, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BooksEntity entity = objectMapper.readValue(bookInfo, BooksEntity.class);
        Map<String, String> result = new HashMap<>();
        try {
            service.insertBook(entity, request);
            result.put("message", "도서 등록 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(Exception e) {
            result.put("message", "도서 등록 실패");
            result.put("description", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);            
        }
        
    }
    
    // 상세 조회
    @GetMapping("/{bookId}")
    public ResponseEntity<Object> selectBook(@PathVariable("bookId") int bookId) throws Exception {
        BooksEntity entity = service.selectBook(bookId);
        if (entity != null) {
            return ResponseEntity.ok(entity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 도서를 찾을 수 없습니다.");
        }
    }
    
    // 수정 처리
    @PutMapping("/{bookId}")
    public ResponseEntity<Object> updateBook(@PathVariable("bookId") int bookId, @RequestBody BookUpdateRequest request) throws Exception {
        try {
            BooksEntity entity = service.selectBook(bookId);
            entity.update(request.getTitle(), request.getAuthor());
            service.updateBook(entity);
            return new ResponseEntity<>("수정 성공", HttpStatus.OK);
        } catch(Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    
    // 삭제 처리
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Object> deleteBoard(@PathVariable("bookId") int bookId) throws Exception {
        try {
            service.deleteBook(bookId);
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    
    // 첨부파일 다운로드 
//    @GetMapping("/board/file")
//    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
//        BoardFileEntity boardFileEntity = boardService.selectBoardFileInfo(idx, boardIdx);
//        if (ObjectUtils.isEmpty(boardFileEntity)) {
//            return;
//        }
//        
//        Path path = Paths.get(boardFileEntity.getStoredFilePath());
//        byte[] file = Files.readAllBytes(path);
//        
//        response.setContentType("application/octet-stream");
//        response.setContentLength(file.length);
//        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileEntity.getOriginalFileName(), "UTF-8") + "\";");
//        response.setHeader("Content-Transfer-Encoding", "binary");
//        response.getOutputStream().write(file);
//        response.getOutputStream().flush();
//        response.getOutputStream().close();
//    }
}
