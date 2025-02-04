package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {
    List<BoardEntity> selectBoardList();
    BoardEntity selectBoardDetail(int boardIdx);
    void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception;
    void updateBoard(BoardEntity boardEntity);
    // void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception;
    void deleteBoard(int boardIdx);
    BoardFileEntity selectBoardFileInfo(int idx, int boardIdx);
}
