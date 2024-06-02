package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
