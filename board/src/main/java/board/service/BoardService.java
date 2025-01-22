package board.service;

import java.util.List;
import board.dto.BoardDto;

public interface BoardService {
    List<BoardDto> selectBoardList();
}
