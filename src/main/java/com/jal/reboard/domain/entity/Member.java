package com.jal.reboard.domain.entity;

import com.jal.reboard.domain.type.RoleType;
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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 30, unique = true)
    @NotBlank(message = "Username에 빈칸을 허용하지 않습니다.")
    private String username;

    @Column(length = 100)
    @NotBlank(message = "Password에 빈칸을 허용하지 않습니다.")
    private String password;

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

}