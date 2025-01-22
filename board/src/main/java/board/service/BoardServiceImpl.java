package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    
    @Override
    public List<BoardDto> selectBoardList() {
        return boardMapper.selectBoardList();
    }
}
