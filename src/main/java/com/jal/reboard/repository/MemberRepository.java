package com.jal.reboard.repository;

import com.jal.reboard.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.username = :username")
    Member findMnoByUsername(@Param("username") String dto);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}
