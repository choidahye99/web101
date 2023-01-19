package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")}) //JPA : 제약조건 Unique 설정
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    private String id; //유저에게 고유하게 부여되는 id

    @Column(nullable = false)
    private String username; //사용자 이름

    private String password; //패스워드

    private String role; //사용자의 롤

    private String authProvider; //이후 OAuth에서 아용할 유저 정보 제공자


}
