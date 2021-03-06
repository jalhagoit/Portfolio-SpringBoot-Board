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

    Page<Board> findByTitleLike(String title, Pageable pageable); // 제목 검색
    Page<Board> findByContentLike(String content, Pageable pageable); // 내용 검색
    Page<Board> findByTitleLikeOrContentLike(String title, String content, Pageable pageable); // 제목 or 내용 검색

    @Query("SELECT b FROM Board b WHERE b.member = :member")
    Page<Board> findBnoByMember(@Param("member") Member member, Pageable pageable);

    @Query("SELECT b.bno FROM Board b WHERE b.member = :member")
    List<Long> findBnoByMember(@Param("member") Member member);

//    제목 두 단어 검색 테스트
    @Query("SELECT b FROM Board b WHERE b.title LIKE CONCAT('%',:keyword1,'%') " +
            "AND b.title LIKE CONCAT('%',:keyword2,'%')")
    List<Board> findTwoWords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2);

}
