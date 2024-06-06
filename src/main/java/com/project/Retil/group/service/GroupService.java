package com.project.Retil.group.service;

import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.entity.GroupMember;

import java.util.List;

public interface GroupService {
    List<GroupInfo> showList();

    List<GroupInfo> showMyList(Long user_id);

    GroupInfo updateIntroduce(Long user_id, Long group_id, String introduce);

    GroupInfo create(Long user_id, String groupName, String introduce, int limit);

    GroupInfo join(Long user_id, String groupName);

    GroupInfo delete(Long user_id, Long group_id);

    GroupMember withdraw(Long user_id, Long group_id);
}