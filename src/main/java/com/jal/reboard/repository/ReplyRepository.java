package com.jal.reboard.repository;

import com.jal.reboard.domain.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(@Param("bno") Long bno);

    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno AND r.depth=1")
    void deleteByBnoDepth1(@Param("bno") Long bno);

    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno AND r.depth=2")
    void deleteByBnoDepth2(@Param("bno") Long bno);

    List<Reply> findByParentLike(Reply rno);


}
