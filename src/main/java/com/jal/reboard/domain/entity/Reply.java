package com.jal.reboard.domain.entity;

import com.jal.reboard.domain.type.CType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class  Reply { // 댓글은 작성, 삭제만 가능.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(length = 200)
    @NotBlank(message = "내용에 빈칸을 허용하지 않습니다.")
    private String content;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creDate;

    private LocalDateTime delDate;

    @Enumerated(EnumType.STRING)
    private CType ctype;


    // 연관관계 맵핑
    @ManyToOne
    @JoinColumn(name = "bno")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "mno")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Reply parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Reply> children = new ArrayList<>();

    private int depth;


    // 연관관계 설정
    public void setBoard(Board board) {
        this.board = board;

        //무한루프 빠지지 않도록 체크
        if(!board.getReplies().contains(this)) {
            board.getReplies().add(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;

        //무한루프 빠지지 않도록 체크
        if(!member.getReplies().contains(this)){
            member.getReplies().add(this);
        }
    }

    public void setCtype(CType ctype) {
        this.ctype = ctype;
    }

    public void setDelDate(LocalDateTime delDate) {
        this.delDate = delDate;
    }

}
