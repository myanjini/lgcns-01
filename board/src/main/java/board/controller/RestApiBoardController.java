package board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
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

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class RestApiBoardController {
    
    @Autowired
    private BoardService boardService;
    
    // 목록 조회
    @GetMapping("/board")
    public List<BoardDto> openBoardList() throws Exception {
        return boardService.selectBoardList();
    }
    
    // 저장 처리
    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardDto boardDto = objectMapper.readValue(boardData, BoardDto.class);
        boardService.insertBoard(boardDto, request);
    }
    
    // 상세 조회
    @GetMapping("/board/{boardIdx}")
    public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        return boardService.selectBoardDetail(boardIdx);        
    }
    
    // 수정 처리
    @PutMapping("/board/{boardIdx}")
    public void updateBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody BoardDto boardDto) throws Exception {
        boardDto.setBoardIdx(boardIdx);
        boardService.updateBoard(boardDto);
    }    
    
    // 삭제 처리
    @DeleteMapping("/board/{boardIdx}")
    public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
    }
    
    // 첨부파일 다운로드 
    @GetMapping("/board/file")
    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileDto)) {
            return;
        }
        
        Path path = Paths.get(boardFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);
        
        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
