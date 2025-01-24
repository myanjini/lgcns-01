package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
    List<BoardDto> selectBoardList();
    void insertBoard(BoardDto boardDto);
    BoardDto selectBoardDetail(int boardIdx);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(BoardDto boardDto);
    void updateHitCnt(int boardIdx);
    void insertBoardFileList(List<BoardFileDto> fileInfoList);
    List<BoardFileDto> selectBoardFileList(int boardIdx);
    BoardFileDto selectBoardFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
