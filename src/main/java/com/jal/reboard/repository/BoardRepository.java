package com.jal.reboard.repository;

import com.jal.reboard.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("SELECT b FROM Board b ORDER BY b.bno DESC")
//    @Query("SELECT b.bno, b.title, b.modDate, b.member.mno FROM Board b ORDER BY b.bno DESC")
    List<Board> findAllDesc();

}
