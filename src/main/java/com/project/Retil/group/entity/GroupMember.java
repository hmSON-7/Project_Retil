/*
package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
*/
