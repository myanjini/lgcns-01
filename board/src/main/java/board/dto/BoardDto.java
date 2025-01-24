package board.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDto {
    private int boardIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String createdDt;
    private String createdId;
    private String updatorDt;
    private String updatorId;
    
    // 첨부 파일 정보를 저장할 필드를 추가
    private List<BoardFileDto> fileInfoList;
}
