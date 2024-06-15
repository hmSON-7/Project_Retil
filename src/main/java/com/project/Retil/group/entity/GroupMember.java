package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DB 그룹 멤버 테이블(M 대 N 관계)
 * id : 일련번호(기본값, 자동 증가)
 * group : 그룹 객체 ( N 대 1 관계. DB에서는 group_id로 표기 )
 * member : 멤버(유저) 객체 ( N 대 1 관계. DB에서는 member_id로 표기 )
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 그룹 멤버 코드. 구별자

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupInfo group;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User_Information member;

    public GroupMember(GroupInfo group, User_Information member) {
        this.group = group;
        this.member = member;
    }
}
