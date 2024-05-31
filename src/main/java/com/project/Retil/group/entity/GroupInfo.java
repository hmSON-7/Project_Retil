package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 그룹 코드. 구별자

    @ManyToOne
    @JoinColumn(nullable = false, name = "group_owner")
    private User_Information groupOwner; // 그룹장. User_Information 객체

    @Column(length = 50) // 그룹 소개란 최대 50자 제한
    private String groupIntroduce; // 그룹 소개

    @Column
    private Integer memberLimit; // 그룹 제한 인원

    @Column
    private Integer memberCurrent; // 현 그룹 소속 인원

    @OneToMany(mappedBy = "group_member")
    private List<GroupMember> memberList = new ArrayList<>(); // 그룹 멤버 테이블

    public GroupInfo(User_Information groupOwner,
                     String groupIntroduce,
                     List<GroupMember> memberList,
                     int memberCurrent) {
        this.groupOwner = groupOwner;
        this.groupIntroduce = groupIntroduce;
        this.memberLimit = 20;
        this.memberCurrent = memberCurrent;
        this.memberList = memberList;
    }
}
