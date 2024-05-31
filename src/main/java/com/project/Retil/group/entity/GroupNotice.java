/*
package com.project.Retil.group.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, name = "group_code")
    private GroupInfo groupCode; // 그룹 코드. GroupInfo 객체 참조

    @Column(nullable = false)
    private String title; // 공지 제목

    @Column(nullable = false)
    private String content; // 공지 내용

    @Column
    private LocalDateTime uploadTime; // 공지 작성 시간
}
*/
