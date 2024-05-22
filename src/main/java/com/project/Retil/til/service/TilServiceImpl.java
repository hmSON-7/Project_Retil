package com.project.Retil.til.service;

import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.repository.TilRepository;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TilServiceImpl implements TilService {
    private final TilRepository tilRepository;
    private final UserRepository userRepository;

    @Override
    public ArrayList<Til> showList(Long userId) {
        return tilRepository.findAllById(userId);
    }

    @Override
    public Til show(Long til_num) {
        return tilRepository.findById(til_num).orElse(null);
    }

    @Override
    public Til save(TilCreateDTO tilCreateDto, Long user_id) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        Til til = new Til(
                tilCreateDto.getTitle(),
                tilCreateDto.getContent(),
                user
        );

        return tilRepository.save(til);
    }

    @Override
    public Til delete(Long user_id, Long til_num) {
        Til target = tilRepository.findById(til_num).orElse(null);

        if(target == null) {
            return null;
        }
        if(!target.getUser().getId().equals(user_id)) {
            return null;
        }

        tilRepository.delete(target);
        return target;
    }
}
