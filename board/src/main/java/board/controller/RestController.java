package board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.dto.BoardInsertRequest;
import board.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RestController {
    @Autowired
    private BoardService boardService;
    
    // 목록 조회
    @GetMapping("/board")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/restBoardList");
        
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        
        return mv;
    }
    
    // 작성 화면
    @GetMapping("/board/write")
    public String openBoardWrite() throws Exception {
        return "/board/restBoardWrite";
    }
    
    // 저장 처리
    @PostMapping("/board/write")
    // public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) throws Exception {
    public String insertBoard(BoardInsertRequest boardInsertRequest, MultipartHttpServletRequest request) throws Exception {
        // 서비스 메서드에 맞춰서 데이터를 변경
        /*
        BoardDto boardDto = new BoardDto();
        boardDto.setTitle(boardInsertRequest.getTitle());
        boardDto.setContents(boardInsertRequest.getContents());
        */
        BoardDto boardDto = new ModelMapper().map(boardInsertRequest, BoardDto.class);
        
        boardService.insertBoard(boardDto, request);
        return "redirect:/board";
    }
    
    // 상세 조회
    @GetMapping("/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
        
        ModelAndView mv = new ModelAndView("/board/restBoardDetail");
        mv.addObject("board", boardDto);
        return mv;
    }
    
    // 수정 처리
    @PutMapping("/board/{boardIdx}")
    public String updateBoard(@PathVariable("boardIdx") int boardIdx, BoardDto boardDto) throws Exception {
        boardDto.setBoardIdx(boardIdx);
        boardService.updateBoard(boardDto);
        return "redirect:/board";
    }
    
    // 삭제 처리
    @DeleteMapping("/board/{boardIdx}")
    public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
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
