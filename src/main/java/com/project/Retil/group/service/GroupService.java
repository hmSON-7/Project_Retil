package com.project.Retil.group.service;

import com.project.Retil.group.dto.GroupChatDTO;
import com.project.Retil.group.dto.GroupDTO;
import com.project.Retil.group.dto.GroupDetailDTO;
import com.project.Retil.group.entity.GroupChat;
import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.entity.GroupMember;

import java.util.ArrayList;
import java.util.List;

public interface GroupService {
    List<GroupDTO> showList();

    List<GroupDTO> showMyList(Long user_id);

    GroupInfo updateIntroduce(Long user_id, Long group_id, String introduce);

    GroupInfo create(Long user_id, String groupName, String introduce, int limit);

    GroupInfo join(Long user_id, String groupName);

    GroupInfo delete(Long user_id, Long group_id);

    GroupMember withdraw(Long user_id, Long group_id);

    GroupChat writeNewChat(Long group_id, Long user_id, String chat);

    ArrayList<GroupChatDTO> showChat(Long group_id);

    GroupDetailDTO getGroupDetail(Long groupId);
}