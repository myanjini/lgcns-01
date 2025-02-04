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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardInsertRequest;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/jpa")
public class JpaBoardController {
    @Autowired
    private JpaBoardService boardService;
    
    // 목록 조회
    @GetMapping("/board")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/jpaBoardList");
        
        List<BoardEntity> list = boardService.selectBoardList();
        mv.addObject("list", list);
        
        return mv;
    }
    
    // 작성 화면
    @GetMapping("/board/write")
    public String openBoardWrite() throws Exception {
        return "/board/jpaBoardWrite";
    }
    
    // 저장 처리
    @PostMapping("/board/write")
    public String insertBoard(BoardInsertRequest boardInsertRequest, MultipartHttpServletRequest request) throws Exception {
        BoardEntity boardEntity = new ModelMapper().map(boardInsertRequest, BoardEntity.class);
        
        boardService.insertBoard(boardEntity, request);
        return "redirect:/jpa/board";
    }
    
    // 상세 조회
    @GetMapping("/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        BoardEntity boardEntity = boardService.selectBoardDetail(boardIdx);
        ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
        mv.addObject("board", boardEntity);
        return mv;
    }
    
    // 수정 처리
    @PutMapping("/board/{boardIdx}")
    public String updateBoard(@PathVariable("boardIdx") int boardIdx, BoardEntity boardEntity) throws Exception {
        boardEntity.setBoardIdx(boardIdx);
        boardService.updateBoard(boardEntity);
        return "redirect:/jpa/board";
    }
    
    // 삭제 처리
    @DeleteMapping("/board/{boardIdx}")
    public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/jpa/board";
    }
    
    // 첨부파일 다운로드 
    @GetMapping("/board/file")
    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileEntity boardFileEntity = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileEntity)) {
            return;
        }
        
        Path path = Paths.get(boardFileEntity.getStoredFilePath());
        byte[] file = Files.readAllBytes(path);
        
        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileEntity.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}
