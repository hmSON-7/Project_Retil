package com.project.Retil.group.repository;

import com.project.Retil.group.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
