package com.jal.reboard.repository;

import com.jal.reboard.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("SELECT b FROM Board b ORDER BY b.bno DESC")
//    @Query("SELECT b.bno, b.title, b.modDate, b.member.mno FROM Board b ORDER BY b.bno DESC")
    List<Board> findAllDesc();

    Page<Board> findByTitleLike(String keyword, Pageable pageable);

//    제목 두 단어 검색 테스트
    @Query("SELECT b FROM Board b WHERE b.title LIKE CONCAT('%',:keyword1,'%') " +
            "AND b.title LIKE CONCAT('%',:keyword2,'%')")
    List<Board> findTwoWords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2);

}
