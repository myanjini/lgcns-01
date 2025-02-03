package board.dto;

import lombok.Data;

@Data
public class BoardListResponse {
    private int boardIdx;
    private String title;
    private int hitCnt;
    private String createdDt;
}
