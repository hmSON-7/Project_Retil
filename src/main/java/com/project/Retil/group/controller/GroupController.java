package com.project.Retil.group.controller;

import com.project.Retil.group.dto.JoinGroupDTO;
import com.project.Retil.group.dto.UpdateGroupDTO;
import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.entity.GroupMember;
import com.project.Retil.group.service.GroupServiceImpl;
import com.project.Retil.group.dto.CreateGroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;
    // 1. 그룹 소개 글 리스트 출력
    // 보여줘야 할 정보 : 즐겨찾기(나중에), 그룹명, 소개글, 그룹장, 현재인원/제한인원
    @GetMapping("/")
    public List<GroupInfo> showList() {
        return groupService.showList();
    }

    // 2. 내가 속한 그룹 리스트 출력
    // 보여줘야 할 정보 : 즐겨찾기(나중에), 그룹명, 소개글, 그룹장, 현재인원/제한인원
    @GetMapping("/{user_id}")
    public List<GroupInfo> showMyList(@PathVariable Long user_id) {
        return groupService.showMyList(user_id);
    }

    // 3. 그룹 소개글 수정
    @PatchMapping("/{group_id}/update")
    public ResponseEntity<GroupInfo> updateIntroduce(@PathVariable Long group_id,
                                                     @RequestBody UpdateGroupDTO updateGroupDto) {
        GroupInfo group = groupService.updateIntroduce(
                updateGroupDto.getUser_id(),
                group_id,
                updateGroupDto.getIntroduce()
        );
        return (group != null) ?
                ResponseEntity.status(HttpStatus.OK).body(group) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 4. 그룹 생성
    @PostMapping("/{user_id}/create")
    public ResponseEntity<GroupInfo> createGroup(@PathVariable Long user_id,
                                                 @RequestBody CreateGroupDTO createGroupDto) {
        GroupInfo newGroup = groupService.create(
                user_id,
                createGroupDto.getGroupName(),
                createGroupDto.getIntroduce(),
                createGroupDto.getLimit()
        );
        return (newGroup != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 5. 그룹 참여
    @PostMapping("/join")
    public ResponseEntity<GroupInfo> joinGroup(@RequestBody JoinGroupDTO joinGroupDto) {

        GroupInfo group = groupService.join(
                joinGroupDto.getUser_id(),
                joinGroupDto.getGroupName()
        );
        return (group != null) ?
                ResponseEntity.status(HttpStatus.OK).body(group) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 6. 그룹 삭제
    @DeleteMapping("/{group_id}/delete")
    public ResponseEntity<GroupInfo> deleteGroup(@PathVariable Long group_id,
                                                 @RequestBody Long user_id) {
        GroupInfo target = groupService.delete(
                user_id, group_id
        );

        return (target != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 7. 그룹 탈퇴
    @PatchMapping("/{group_id}/withdraw")
    public ResponseEntity<GroupMember> withdrawGroup(@PathVariable Long group_id,
                                                     @RequestBody Long user_id) {
        GroupMember target = groupService.withdraw(
                user_id, group_id
        );

        return (target != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}