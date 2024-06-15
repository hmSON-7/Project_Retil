package com.project.Retil.group.entity;

import com.project.Retil.userAccount.Entity.User_Information;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DB 그룹 정보 테이블
 * id : 일련번호(기본값, 자동 증가)
 * groupName : 그룹명
 * groupOwner : 그룹장 객체( N 대 1 관계. DB에서는 group_owner로 표기 )
 * groupIntroduce : 그룹 한 줄 소개. 최대 50자로 제한
 * memberLimit : 그룹 제한 인원. 그룹원의 수는 해당 조건을 초과할 수 없음
 * memberCurrent : 현재 그룹 소속 인원 수
 * memberList : 소속 그룹원 리스트( 1 대 N 관계 )
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 그룹 코드. 구별자

    @Column
    private String groupName; // 그룹 이름

    @ManyToOne
    @JoinColumn(nullable = false, name = "group_owner")
    private User_Information groupOwner; // 그룹장. User_Information 객체

    @Column(length = 50) // 그룹 소개란 최대 50자 제한
    private String groupIntroduce; // 그룹 소개

    @Column
    private int memberLimit; // 그룹 제한 인원

    @Column
    private int memberCurrent; // 현 그룹 소속 인원

    @OneToMany(mappedBy = "group")
    private List<GroupMember> memberList = new ArrayList<>(); // 그룹 멤버 테이블

    public GroupInfo(User_Information groupOwner,
                     String groupIntroduce,
                     int memberLimit) {
        this.groupOwner = groupOwner;
        this.groupIntroduce = groupIntroduce;
        this.memberLimit = memberLimit;
        this.memberCurrent = 1;
        this.memberList = null;
    }

    public void changeGroupIntroduce(String introduce) {
        this.groupIntroduce = introduce;
    }

    public void changeMemberCurrent(int num) {
        this.memberCurrent = num;
    }
}