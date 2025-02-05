package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {
    @Autowired
    private JpaBoardRepository jpaBoardRepository;
    
    @Autowired
    private FileUtils fileUtils;
    
    @Override
    public List<BoardEntity> selectBoardList() {
        return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
    }

    @Override
    public BoardEntity selectBoardDetail(int boardIdx) {
        Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
        if (optional.isPresent()) {
            BoardEntity board = optional.get();
            
            board.setHitCnt(board.getHitCnt() + 1);
            jpaBoardRepository.save(board);
            
            return board;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteBoard(int boardIdx) {
        jpaBoardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardFileEntity selectBoardFileInfo(int idx, int boardIdx) {
        return jpaBoardRepository.findBoardFile(boardIdx, idx);
    }

    @Override
    public void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boardEntity.setCreatedId(username);
        
        List<BoardFileEntity> list = fileUtils.parseFileInfo(boardEntity.getBoardIdx(), request);
        if (!CollectionUtils.isEmpty(list)) {
            boardEntity.setFileInfoList(list);
        }
        jpaBoardRepository.save(boardEntity);        
    }

    @Override
    public void updateBoard(BoardEntity boardEntity) {
        BoardEntity existingBoard = jpaBoardRepository.findById(boardEntity.getBoardIdx()).orElse(null);
        if (existingBoard != null) {
            boardEntity.setFileInfoList(existingBoard.getFileInfoList());
        } 
        jpaBoardRepository.save(boardEntity);    
    }
}
