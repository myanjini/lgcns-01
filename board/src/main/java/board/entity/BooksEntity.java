package board.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_jpa_book")
@Getter
@Setter
@DynamicUpdate
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    private String publisher;
    
    private String publishedDate;
    
    @Column(unique = true)
    private String isbn;
    
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_dt", nullable = false, updatable = false)
    private LocalDateTime createdDt;

    @UpdateTimestamp
    private LocalDateTime updatorDt;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private List<BookImagesEntity> bookImages;
    
    public void update(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
