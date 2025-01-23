package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

// @Transactional
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    
    @Override
    public List<BoardDto> selectBoardList() {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto) {
        // 로그인한 사용자를 글쓴이로 설정
        // TODO. 로그인한 사용자의 ID로 변경
        boardDto.setCreatedId("hong");
        
        boardMapper.insertBoard(boardDto);
    }

    // @Transactional
    @Override
    public BoardDto selectBoardDetail(int boardIdx) {
        boardMapper.updateHitCnt(boardIdx);
        int i = 10 / 0;
        return boardMapper.selectBoardDetail(boardIdx);
    }

    @Override
    public void updateBoard(BoardDto boardDto) {
        // TODO. 로그인한 사용자 아이디로 변경
        boardDto.setUpdatorId("go");
        boardMapper.updateBoard(boardDto);        
    }

    @Override
    public void deleteBoard(int boardIdx) {
        BoardDto boardDto = new BoardDto(); 
        boardDto.setBoardIdx(boardIdx);
        // TODO. 로그인한 사용자 아이디로 변경
        boardDto.setUpdatorId("go");
        boardMapper.deleteBoard(boardDto);        
    }
}
