package com.jal.reboard.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jal.reboard.domain.type.RoleType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 30, unique = true, updatable = false)
    @NotBlank(message = "Username에 빈칸을 허용하지 않습니다.")
    private String username;

    @Column(length = 100)
    @NotBlank(message = "Password에 빈칸을 허용하지 않습니다.")
    @JsonIgnore
    private String password; // transient 넣으니까 회원가입할 때 비번 저장이 안 됨.

//    @Column(unique = true)
//    private String email;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private RoleType roleType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime modDate;


//    private LocalDateTime delDate;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @JsonIgnore
    private List<Reply> replies = new ArrayList<>();

    public void addBoard(Board board) {
        this.boards.add(board);
        if (board.getMember() != this) { //무한루프 빠지지 않도록 체크
            board.setMember(this);
        }
    }

}
