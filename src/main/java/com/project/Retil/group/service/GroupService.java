package com.project.Retil.group.service;

import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.til.dto.CreateGroupDTO;

import java.util.List;

public interface GroupService {
    List<GroupInfo> showList();

    List<GroupInfo> showMyList(Long user_id);

    GroupInfo updateIntroduce(Long user_id, String groupName);

    GroupInfo create(Long user_id, String groupName, String introduce, int limit);

    GroupInfo join(Long user_id, String groupName);

    GroupInfo delete(Long user_id, String groupName);

    GroupInfo withdraw(Long user_id, String groupName);
}
