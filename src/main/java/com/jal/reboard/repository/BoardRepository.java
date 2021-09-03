package com.jal.reboard.repository;

import com.jal.reboard.domain.entity.Board;
import com.jal.reboard.domain.entity.Member;
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

    @Query("SELECT m FROM Member m WHERE m.username = :username")
    Member findMnoByUsername(@Param("username") String dto);

    Page<Board> findByTitleLike(String keyword, Pageable pageable);

}
