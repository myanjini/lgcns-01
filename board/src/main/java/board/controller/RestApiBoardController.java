package board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import board.dto.BoardListResponse;
import board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:5173")
public class RestApiBoardController {
    
    @Autowired
    private BoardService boardService;
    
    // 목록 조회
//    @CrossOrigin(origins = "http://localhost:5173")
    @Operation(summary = "게시판 목록 조회", description = "등록된 게시물 목록을 조회해서 반환합니다.")
    @GetMapping("/board")
    public List<BoardListResponse> openBoardList() throws Exception {
        List<BoardDto> boardList = boardService.selectBoardList();
        
        List<BoardListResponse> results = new ArrayList<>();
        /*
        for (BoardDto dto : boardList) {
            BoardListResponse res = new BoardListResponse();
            res.setBoardIdx(dto.getBoardIdx());
            res.setTitle(dto.getTitle());
            res.setHitCnt(dto.getHitCnt());
            res.setCreatedDt(dto.getCreatedDt());
            results.add(res);
        }
        */
        boardList.forEach(dto -> results.add(new ModelMapper().map(dto, BoardListResponse.class)));
        return results;
    }
    
    // 저장 처리
    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardDto boardDto = objectMapper.readValue(boardData, BoardDto.class);
        Map<String, String> result = new HashMap<>();
        try {
            boardService.insertBoard(boardDto, request);
            result.put("message", "게시판 저장 성공");
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(Exception e) {
            result.put("message", "게시판 저장 실패");
            result.put("description", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);            
        }
        
    }
    
    // 상세 조회
    @Operation(summary = "게시판 상세 조회", description = "게시물 아이디와 일치하는 게시물의 상세 정보를 조회해서 반환합니다.")
    @Parameter(name = "boardIdx", description = "게시물 아이디", required = true)
    @GetMapping("/board/{boardIdx}")
    public ResponseEntity<Object> openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
        if (boardDto == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("code", HttpStatus.NOT_FOUND.value());
            result.put("name", HttpStatus.NOT_FOUND.name());
            result.put("message", "게시판 번호 " + boardIdx + "와 일치하는 게시물이 존재하지 않습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(boardDto);
            // return ResponseEntity.ok(boardDto);
        }
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
