package com.project.Retil.til.service;

import com.project.Retil.til.repository.TilRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TilServiceImpl {
    private final TilRepository tilRepository;

    @Override
    public ArrayList<Til> showList(Long userId) {
        return tilRepository.findAllById(userId);
    }

    @Override
    public Til show(Long til_num) {
        return tilRepository.findById(til_num).orElse(null);
    }

    @Override
    public Til create(TilCreateDTO tilCreateDto, Long user_id) {
        Til til = new Til(
                tilCreateDto.getTitle(),
                tilCreateDto.getContent(),
                user_id
        );

        return tilRepository.save(til);
    }

    @Override
    public Til delete(Long user_id, Long til_num) {
        Til target = tilRepository.findById(til_num).orElse(null);

        if(target == null) {
            return null;
        }
        if(!target.getUser().getUserId().equals(user_id)) {
            return null;
        }

        tilRepository.delete(target);
        return target;
    }
}
