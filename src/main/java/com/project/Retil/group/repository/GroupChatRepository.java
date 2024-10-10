package com.project.Retil.group.repository;

import com.project.Retil.group.entity.GroupChat;
import com.project.Retil.group.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {

    ArrayList<GroupChat> findAllByGroupCode(GroupInfo group);
}