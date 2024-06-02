package com.project.Retil.til.controller;

import com.project.Retil.til.dto.AddSubjectDTO;
import com.project.Retil.til.dto.TempSaveDTO;
import com.project.Retil.til.dto.TilCreateDTO;
import com.project.Retil.til.dto.TilListDTO;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.service.TilServiceImpl;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.chatgpt.ChatGPTService;
import com.project.Retil.question.entity.Question; // Question 클래스 임포트
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
    private final ChatGPTService chatGPTService; // ChatGPTService 주입

    // 1. TIL 전체 리스트 조회
    @GetMapping("/")
    public List<TilListDTO> showList(@PathVariable Long user_id) {
        return tilService.showList(user_id);
    }

    // 2. TIL 과목별 리스트 조회
    @GetMapping("/subject/{subjectName}")
    public List<TilListDTO> showListInSubject(@PathVariable Long user_id,
                                              @PathVariable String subjectName) {
        return tilService.showListInSubject(user_id, subjectName);
    }

    // 3. TIL 에디터 보기
    @GetMapping("/{til_num}")
    public Til show(@PathVariable Long user_id, @PathVariable Long til_num) {
        return tilService.show(user_id, til_num);
    }

    // 4. TIL 작성 내용 임시 저장 : 공부 시간만 가져와 저장
    @PostMapping("/write/temp")
    public ResponseEntity<User_Rank> tempSave(@PathVariable Long user_id,
                                              @RequestBody TempSaveDTO temp) {
        User_Information user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        TilSubject subject = tilService.searchSubject(temp.getSubjectName(), user);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User_Rank userRank = tilService.timeSave(user, temp.getTime(), subject);
        return (userRank != null) ?
                ResponseEntity.status(HttpStatus.OK).body(userRank) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 5. TIL 작성 완료 후 저장
    @PostMapping("/write")
    public ResponseEntity<Til> save(@RequestBody TilCreateDTO tilCreateDto,
                                    @PathVariable Long user_id) {
        try {
            Til created = tilService.save(tilCreateDto, user_id);
            if (created != null) {
                // TIL 저장 후 ChatGPT를 호출하여 질문 생성 및 저장
                List<Question> questions = chatGPTService.generateQuestions(created); // Til 객체 전달
                questions.forEach(tilService::saveUniqueQuestion); // 질문 저장 로직 추가
                return ResponseEntity.status(HttpStatus.OK).body(created);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 6. TIL 삭제
    @DeleteMapping("/{til_num}")
    public ResponseEntity<Void> delete(@PathVariable Long user_id, @PathVariable Long til_num) {
        Til deleted = tilService.delete(user_id, til_num);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 7. 과목 등록
    @PostMapping("/subject")
    public ResponseEntity<TilSubject> addSubject(@PathVariable Long user_id,
                                                 @RequestBody AddSubjectDTO addSubjectDto) {
        TilSubject addedSubject = tilService.addSubject(
                user_id, addSubjectDto.getSubjectName(), addSubjectDto.getColor());

        return (addedSubject != null) ?
                ResponseEntity.status(HttpStatus.OK).body(addedSubject) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
