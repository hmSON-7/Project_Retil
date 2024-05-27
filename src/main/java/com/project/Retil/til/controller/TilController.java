package com.project.Retil.til.controller;

import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.service.TilServiceImpl;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/til/{user_id}")
@RequiredArgsConstructor
public class TilController {
    private final TilServiceImpl tilService;
    private final UserRepository userRepository;

    // 1. TIL 전체 리스트 조회
    @GetMapping("/")
    public List<TilListDTO> showList(@PathVariable Long user_id) {
        return tilService.showList(user_id);
    }

    // 2. TIL 과목별 리스트 조회
    @GetMapping("/{subjectName}")
    public List<TilListDTO> showListInSubject(@PathVariable Long user_id,
                                              @PathVariable String subjectName) {
        return tilService.showListInSubject(user_id, subjectName);
    }

    // 3. TIL 에디터 보기
    @GetMapping("/{til_num}")
    public Til show(@PathVariable Long til_num, @PathVariable String user_id) {
        return tilService.show(til_num);
    }

    // 4. TIL 작성 내용 임시 저장 : 공부 시간만 가져와 저장 ( 미완성. 유저 랭크 객체 먼저 만들어야 함 )
    @PostMapping("/write/temp")
    public ResponseEntity<User_Rank> tempSave(@PathVariable Long user_id,
                                              @RequestBody String subjectName,
                                              @RequestBody Long time) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        TilSubject subject = tilService.searchSubject(subjectName, user);
        User_Rank userRank = tilService.timeSave(user, time, subject);
        return (userRank != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 5. TIL 작성 완료 후 저장
    @PostMapping("/write")
    public ResponseEntity<Til> save(@RequestBody TilCreateDTO tilCreateDto,
                                    @RequestBody Long time,
                                    @PathVariable Long user_id) {
        Til created = tilService.save(tilCreateDto, user_id, time);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 6. TIL 삭제
    @DeleteMapping("/{til_num}")
    public ResponseEntity<Til> delete(@PathVariable Long til_num,
                                      @PathVariable Long user_id) {
        Til deleted = tilService.delete(user_id, til_num);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 7. 과목 등록
    @PostMapping("/")
    public ResponseEntity<TilSubject> addSubject(@PathVariable Long user_id,
                                                 @RequestBody String subjectName,
                                                 @RequestBody String color) {
        TilSubject addedSubject = tilService.addSubject(user_id, subjectName, color);
        return (addedSubject != null) ?
                ResponseEntity.status(HttpStatus.OK).body(addedSubject) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
