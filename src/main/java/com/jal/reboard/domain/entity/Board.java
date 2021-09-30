package com.jal.reboard.domain.entity;

import com.jal.reboard.domain.type.CType;
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
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 100)
    @NotBlank(message = "제목에 빈칸을 허용하지 않습니다.")
    private String title;

    @Lob
    @NotBlank(message = "내용에 빈칸을 허용하지 않습니다.")
    private String content;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creDate;

    @UpdateTimestamp
    private LocalDateTime modDate;

    @Enumerated(EnumType.STRING)
    private CType ctype;


    @ManyToOne
    @JoinColumn(name = "mno")
    private Member member;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    //연관관계 설정
    public void setMember(Member member) {
        this.member = member;

        //무한루프 빠지지 않도록 체크
        if(!member.getBoards().contains(this)){
            member.getBoards().add(this);
        }
    }


    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }


}
