package com.project.Retil.userAccount.service;

import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.dto.JoinRequestDTO;
import com.project.Retil.userAccount.dto.LoginRequestDTO;

public interface UserService {

    User_Information signUp(JoinRequestDTO joinRequestDto);

    User_Information login(LoginRequestDTO loginRequestDto);

    String sendMail(String email);

    User_Information pwChange(String email, String password);

    User_Information deleteUser(Long user_id, String password);

    User_Information findUser(Long user_id);

    User_Rank findUserRank(User_Information user);

    User_Information changeNickname(Long user_id, String newNickname);
}
