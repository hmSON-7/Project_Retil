package com.project.Retil.group.repository;

import com.project.Retil.group.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository <GroupInfo, Long>{

    GroupInfo findByGroupName(String groupName);
}