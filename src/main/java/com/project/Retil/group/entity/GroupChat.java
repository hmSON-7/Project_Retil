package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DB 그룹 채팅 테이블(M 대 N 관계 테이블)
 * id : 일련번호(기본값, 자동 증가)
 * groupCode : 해당 그룹 코드(N대1 관계, DB에서는 group_code로 표기)
 * chatComment : 채팅 내용
 * member : 채팅 작성자 객체( 그룹 소속 인원, DB에서는 member로 표기 )
 * timeStamp : 타임 스탬프. 채팅 작성 시각
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupChat {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "group_code")
    private GroupInfo groupCode; // 그룹 코드, GroupInfo 객체 참조

    @Column(nullable = false)
    private String chatComment;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member")
    private User_Information member;

    @Column
    private LocalDateTime timeStamp;

    public GroupChat(GroupInfo groupCode,
                     String comment,
                     User_Information member,
                     LocalDateTime timeStamp) {

        this.groupCode = groupCode;
        this.chatComment = comment;
        this.member = member;
        this.timeStamp = timeStamp;
    }
}
