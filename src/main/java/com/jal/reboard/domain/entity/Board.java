package com.jal.reboard.domain.entity;

import com.jal.reboard.domain.type.CType;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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


    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }


}
