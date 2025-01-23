package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;

public interface BoardService {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request);
    BoardDto selectBoardDetail(int boardIdx);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(int boardIdx);
}
