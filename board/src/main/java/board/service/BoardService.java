package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

public interface BoardService {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request);
    BoardDto selectBoardDetail(int boardIdx);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(int boardIdx);
    BoardFileDto selectBoardFileInfo(int idx, int boardIdx);
}
