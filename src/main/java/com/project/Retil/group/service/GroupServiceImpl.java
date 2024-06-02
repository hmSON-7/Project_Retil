package com.project.Retil.group.service;

import com.project.Retil.group.entity.GroupInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService{

    // 1. 그룹 리스트
    @Override
    public List<GroupInfo> showList() {
        return null;
    }

    // 2. 내가 속한 그룹 리스트
    @Override
    public List<GroupInfo> showMyList(Long user_id) {
        return null;
    }

    // 3. 그룹 소개 수정
    @Override
    public GroupInfo updateIntroduce(Long user_id, String groupName) {
        return null;
    }

    // 4. 그룹 생성
    @Override
    public GroupInfo create(Long user_id, String groupName, String introduce, int limit) {
        return null;
    }

    // 5. 그룹 참여
    @Override
    public GroupInfo join(Long user_id, String groupName) {
        return null;
    }

    // 6. 그룹 제거
    @Override
    public GroupInfo delete(Long user_id, String groupName) {
        return null;
    }

    // 7. 그룹 탈퇴
    @Override
    public GroupInfo withdraw(Long user_id, String groupName) {
        return null;
    }
}
