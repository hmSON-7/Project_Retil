package com.project.Retil.group.repository;

import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.entity.GroupMember;
import com.project.Retil.userAccount.Entity.User_Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    ArrayList<GroupMember> findAllByMember(User_Information member);

    void deleteGroupMemberByGroupAndMember(GroupInfo group, User_Information member);

    GroupMember findGroupMemberByGroupAndMember(GroupInfo group, User_Information member);
}