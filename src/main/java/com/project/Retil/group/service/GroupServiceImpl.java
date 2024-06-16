package com.project.Retil.group.service;

import com.project.Retil.group.dto.GroupChatDTO;
import com.project.Retil.group.dto.GroupDTO;
import com.project.Retil.group.dto.GroupDetailDTO;
import com.project.Retil.group.dto.GroupMemberDTO;
import com.project.Retil.group.entity.GroupChat;
import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.entity.GroupMember;
import com.project.Retil.group.repository.GroupChatRepository;
import com.project.Retil.group.repository.GroupMemberRepository;
import com.project.Retil.group.repository.GroupRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRankRepository;
import com.project.Retil.userAccount.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupChatRepository groupChatRepository;
    private final UserRepository userRepository;
    private final UserRankRepository userRankRepository;

    // 1. 그룹 리스트
    @Override
    public List<GroupDTO> showList() {
        List<GroupInfo> groupList =  groupRepository.findAll();

        List<GroupDTO> list = new ArrayList<>();
        for(GroupInfo group : groupList) {
            list.add(new GroupDTO(
                    group.getId(),
                    group.getGroupName(),
                    group.getGroupOwner().getNickname(),
                    group.getGroupIntroduce(),
                    group.getMemberLimit(),
                    group.getMemberCurrent()
            ));
        }

        return list;
    }

    // 2. 내가 속한 그룹 리스트
    @Override
    public List<GroupDTO> showMyList(Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        List<GroupMember> myList = groupMemberRepository.findAllByMember(user);
        List<GroupDTO> targetList = new ArrayList<>();
        for(GroupMember member : myList) {
            GroupInfo group = member.getGroup();

            targetList.add(new GroupDTO(
                    group.getId(),
                    group.getGroupName(),
                    group.getGroupOwner().getNickname(),
                    group.getGroupIntroduce(),
                    group.getMemberLimit(),
                    group.getMemberCurrent()
            ));
        }

        return targetList;
    }

    // 3. 그룹 소개 수정
    @Override
    public GroupInfo updateIntroduce(Long user_id, Long group_id, String introduce) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo group = groupRepository.findById(group_id).orElse(null);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        if(!group.getGroupOwner().equals(user)) {
            throw new IllegalArgumentException("그룹 소개를 수정할 권한이 없습니다.");
        }

        group.changeGroupIntroduce(introduce);
        return groupRepository.save(group);
    }

    // 4. 그룹 생성
    @Override
    public GroupInfo create(Long user_id, String groupName, String introduce, int limit) {
        User_Information owner = userRepository.findById(user_id).orElse(null);
        if(owner == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo newGroup = new GroupInfo(
                owner, groupName, introduce, limit
        );
        groupRepository.save(newGroup);
        addMember(owner, newGroup);

        return newGroup;
    }

    // 5. 그룹 참여
    @Override
    @Transactional
    public GroupInfo join(Long user_id, String groupName) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo group = groupRepository.findByGroupName(groupName);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        int cur = group.getMemberCurrent();
        if(group.getMemberLimit() <= cur) {
            throw new IllegalArgumentException("정원 초과!");
        }

        group.changeMemberCurrent(cur + 1);
        groupRepository.save(group);
        addMember(user, group);

        return group;
    }

    // 그룹 생성 및 참여시 사용할 그룹 멤버 객체 추가 메서드
    public void addMember(User_Information newMember, GroupInfo group) {
        GroupMember member = new GroupMember(group, newMember);
        group.addMember(member);
        groupMemberRepository.save(member);
    }

    // 6. 그룹 제거
    @Override
    public GroupInfo delete(Long user_id, Long group_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo group = groupRepository.findById(group_id).orElse(null);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        if(!group.getGroupOwner().equals(user)) {
            throw new IllegalArgumentException("그룹을 제거할 권한이 없습니다.");
        }

        groupRepository.deleteById(group_id);

        return group;
    }

    // 7. 그룹 탈퇴
    @Override
    public GroupMember withdraw(Long user_id, Long group_id) {

        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo group = groupRepository.findById(group_id).orElse(null);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        GroupMember member = groupMemberRepository.findGroupMemberByGroupAndMember(group, user);
        if(member == null) {
            throw new IllegalArgumentException("그룹 멤버가 아닙니다.");
        }

        groupMemberRepository.deleteGroupMemberByGroupAndMember(group, user);
        group.removeMember(member);
        return member;
    }

    // 8. 그룹 챗 작성
    @Override
    public GroupChat writeNewChat(Long user_id, Long group_id, String chat) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if(user == null) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

        GroupInfo group = groupRepository.findById(group_id).orElse(null);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        GroupMember member = groupMemberRepository.findGroupMemberByGroupAndMember(group, user);
        if(member == null) {
            throw new IllegalArgumentException("그룹 멤버가 아닙니다.");
        }

        GroupChat newChat = new GroupChat(
                group,
                chat,
                user,
                LocalDateTime.now()
        );

        return groupChatRepository.save(newChat);
    }

    @Override
    public ArrayList<GroupChatDTO> showChat(Long group_id) {
        GroupInfo group = groupRepository.findById(group_id).orElse(null);
        if(group == null) {
            throw new IllegalArgumentException("존재하지 않는 그룹입니다.");
        }

        ArrayList<GroupChat> groupChatList = groupChatRepository.findAllByGroupCode(group);

        ArrayList<GroupChatDTO> targetList = new ArrayList<>();
        for(GroupChat chat : groupChatList) {
            User_Rank rank = userRankRepository.findByUser(chat.getMember());
            targetList.add(new GroupChatDTO(
                    chat.getMember().getId(),
                    chat.getMember().getNickname(),
                    rank.getUserRank(),
                    chat.getChatComment(),
                    chat.getTimeStamp()
            ));
        }

        return targetList;
    }

    @Override
    public GroupDetailDTO getGroupDetail(Long groupId) {
        GroupInfo group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 그룹입니다."));
        List<GroupMember> members = groupMemberRepository.findAllByGroup(group);
        List<GroupMemberDTO> memberDTOs = new ArrayList<>();

        for (GroupMember member : members) {
            User_Information user = member.getMember();
            User_Rank userRank = userRankRepository.findByUser(user);
            GroupMemberDTO dto = new GroupMemberDTO(
                    user.getId(),
                    user.getNickname(),
                    userRank.getTodayStudyTime(),
                    userRank.getUserRank() // Setting the new field
            );
            memberDTOs.add(dto);
        }

        return new GroupDetailDTO(
                group.getId(),
                group.getGroupName(),
                group.getGroupIntroduce(),
                group.getGroupOwner().getNickname(),
                group.getMemberCurrent(),
                group.getMemberLimit(),
                memberDTOs
        );
    }
}