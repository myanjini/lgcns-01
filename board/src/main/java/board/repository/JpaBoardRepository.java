package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

@Repository
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer> {
    List<BoardEntity> findAllByOrderByBoardIdxDesc();
    
    @Query("SELECT file FROM BoardFileEntity file WHERE file.board.boardIdx = :boardIdx AND file.idx = :idx")
    BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);    
}
