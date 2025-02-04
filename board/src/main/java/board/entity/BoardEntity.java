package board.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_jpa_board")
@Data
// @DynamicUpdate  // 변경된 필드만 업데이트
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int boardIdx;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String contents;
    
    @Column(nullable = false)
    private int hitCnt = 0;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDt;
    
    @Column(nullable = false, updatable = false)
    private String createdId;
    
    @UpdateTimestamp
    private LocalDateTime updatorDt;
    
    private String updatorId;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_idx")
    private List<BoardFileEntity> fileInfoList;
}
