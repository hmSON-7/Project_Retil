package com.project.Retil.group.repository;

import com.project.Retil.group.entity.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository <GroupInfo, Long>{
}
