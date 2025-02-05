package board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.entity.BooksEntity;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Integer> {
}
