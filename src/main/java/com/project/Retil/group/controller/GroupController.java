package com.project.Retil.group.controller;

import com.project.Retil.group.entity.GroupInfo;
import com.project.Retil.group.service.GroupServiceImpl;
import com.project.Retil.til.dto.CreateGroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;
    // 1. 그룹 소개 글 리스트 출력
    // 보여줘야 할 정보 : 즐겨찾기(나중에), 그룹명, 소개글, 그룹장, 현재인원/제한인원

    // 2. 내가 속한 그룹 리스트 출력
    // 보여줘야 할 정보 : 즐겨찾기(나중에), 그룹명, 소개글, 그룹장, 현재인원/제한인원

    // 3. 별 누르면 즐겨찾기 활성화(선택, 나중에)

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
        return null;
    }

    // 5. 그룹 참가

    // 6. 그룹 삭제

    // 7. 그룹 탈퇴

    // 8. 그룹 소개글 수정
}
