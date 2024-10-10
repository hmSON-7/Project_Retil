package com.project.Retil.til.controller;

import com.project.Retil.til.dto.*;
import com.project.Retil.til.entity.Til;
import com.project.Retil.til.entity.TilSubject;
import com.project.Retil.til.service.TilServiceImpl;
import com.project.Retil.userAccount.Entity.User_Information;
import com.project.Retil.userAccount.Entity.User_Rank;
import com.project.Retil.userAccount.Repository.UserRepository;
import com.project.Retil.chatgpt.ChatGPTService;
import com.project.Retil.question.entity.Question; // Question 클래스 임포트
import com.project.Retil.userAccount.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/til/{user_id}")
@RequiredArgsConstructor
public class TilController {

    private final TilServiceImpl tilService;
    private final UserService userService;
    private final ChatGPTService chatGPTService; // ChatGPTService 주입

    /**
     * 1. TIL 전체 리스트 조회
     * 요청한 유저가 작성한 모든 TIL 리스트 반환
     * @param user_id 리스트 열람을 요청한 유저 번호. 해당 번호로 유저 객체 탐색
     * @return 해당 유저가 작성한 모든 TIL 리스트 반환
     */
    @GetMapping("/")
    public List<TilListDTO> showList(@PathVariable Long user_id) {
        return tilService.showList(user_id);
    }

    /**
     * 2. 과목별 TIL 리스트 조회
     * 요청한 사용자가 작성한 TIL 중 선택한 과목에 해당하는 모든 TIL 리스트 반환
     * @param user_id 리스트 열람을 요청한 유저 번호. 해당 번호로 유저 객체 탐색
     * @param subjectName 열람하고자 하는 과목의 이름
     * @return 사용자가 선택한 과목에 해당하는 모든 TIL 리스트 반환
     */
    @GetMapping("/subject/{subjectName}")
    public List<TilListDTO> showListInSubject(@PathVariable Long user_id,
                                              @PathVariable String subjectName) {
        return tilService.showListInSubject(user_id, subjectName);
    }

    /**
     * 3. TIL 에디터 보기
     * 사용자가 선택한 TIL의 내용 열람
     * @param user_id TIL 열람을 요청한 유저 번호. 해당 번호로 유저 객체 탐색
     * @param til_num 열람하고자 하는 TIL 번호
     * @return TIl의 제목, 과목명, 내용, 작성 시각 반환
     */
    @GetMapping("/{til_num}")
    public TilDTO show(@PathVariable Long user_id, @PathVariable Long til_num) {
        Til til = tilService.show(user_id, til_num);

        return new TilDTO(
                til.getTitle(),
                til.getTilSubject().getSubjectName(),
                til.getContent(),
                til.getSaveTime()
        );
    }

    /**
     * 4. TIL 작성 내용 임시 저장
     * 임시 저장 시 작성 내용은 로컬 스토리지에 분리, 서버에서는 공부 시간만 처리
     *
     * @param user_id TIL 임시 저장을 요청한 유저 번호. 해당 번호로 유저 객체 탐색
     * @param temp 선택한 과목명과 공부한 시간 값을 가져옴. 전체 공부 시간과 과목별 공부 시간을 동시에 처리하기 위함
     * @return 임시 저장에 성공하더라도 특정 값을 반환하지는 않음
     */
    @PostMapping("/write/temp")
    public ResponseEntity<User_Rank> tempSave(@PathVariable Long user_id,
                                              @RequestBody TempSaveDTO temp) {

        User_Information user = userService.findUser(user_id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        TilSubject subject = tilService.searchSubject(temp.getSubjectName(), user);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User_Rank userRank = tilService.timeSave(user, temp.getTime(), subject);
        return (userRank != null) ?
            ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 5. TIL 작성 내용 저장
     * 작성한 내용을 가져와 저장하고, 공부한 시간에 대한 처리를 진행
     * 작성된 TIL 내용을 기반으로 ChatGPT를 이용해 오늘의 문제 제작
     * @param tilCreateDto 작성된 TIL의 과목명, 제목, 내용, 공부한 시간
     * @param user_id TIL 작성자의 유저 번호
     * @return TIL 저장 및 문제 작성 완료시 OK 사인 반환, 오류 발생시 BAD_REQUEST 또는 ERROR 반환
     */
    @PostMapping("/write")
    public ResponseEntity<Til> save(@RequestBody TilCreateDTO tilCreateDto,
                                    @PathVariable Long user_id) {
        try {
            User_Information user = userService.findUser(user_id);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Til created = tilService.save(tilCreateDto, user_id);
            if (created != null) {
                // TIL 저장 후 ChatGPT를 호출하여 질문 생성 및 저장
                /*chatGPTService.generateAndSaveQuestions(created, user);*/
                return ResponseEntity.status(HttpStatus.OK).build();
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

    /**
     * 6. TIL 삭제
     * 사용자로부터 요청받은 TIL을 삭제
     * @param user_id TIL 삭제를 요청한 사용자의 번호
     * @param til_num 삭제 요청을 받은 TIL의 번호
     * @return 해당 TIL을 DB에서 찾아서 제거. 성공하더라도 특정 값을 반환하지는 않음
     */
    @DeleteMapping("/{til_num}")
    public ResponseEntity<Void> delete(@PathVariable Long user_id, @PathVariable Long til_num) {
        Til deleted = tilService.delete(user_id, til_num);
        return (deleted != null) ?
            ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 7. 과목 등록
     * 사용자로부터 과목명과 과목에 대한 색상을 입력받아 새 과목 생성
     * @param user_id 과목 등록을 요청한 사용자의 번호
     * @param addSubjectDto 과목 등록을 위해 작성한 과목명, 색상
     * @return 과목 추가 성공시 OK 사인과 함께 추가된 과목 객체 반환
     */
    @PostMapping("/subject")
    public ResponseEntity<TilSubject> addSubject(@PathVariable Long user_id,
                                                 @RequestBody AddSubjectDTO addSubjectDto) {
        TilSubject addedSubject = tilService.addSubject(
            user_id, addSubjectDto.getSubjectName(), addSubjectDto.getColor());

        return (addedSubject != null) ?
            ResponseEntity.status(HttpStatus.OK).body(addedSubject) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 8. 에디터 작성시 과목 리스트 조회
     * 에디터 작성시 미리 과목을 선택할 수 있도록 리스트 조회
     * @param user_id TIL 작성자의 번호
     * @return 해당 사용자가 만들어 두었던 과목들의 이름을 리스트로 반환
     */
    @GetMapping("/write")
    public List<TilSubjectDTO> showSubjectList(@PathVariable Long user_id) {
        return tilService.showSubjectList(user_id);
    }

    /**
     * 9. 선택한 과목 삭제
     * 유저가 선택한 과목과 그 과목에 해당하는 TIL 삭제
     * @param user_id 과목 삭제를 요청한 사용자의 번호
     * @param subjectName 삭제 요청을 받은 대상 과목
     * @return DB에서 해당 과목 삭제. 성공하더라도 특별한 값을 반환하지는 않음
     */
    @DeleteMapping("/subject")
    public ResponseEntity<TilSubject> deleteSubject(@PathVariable Long user_id,
                                                    @RequestBody String subjectName) {

        TilSubject target = tilService.deleteSubject(user_id, subjectName);

        return (target != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 10. TIL 즐겨찾기 설정
     * 선택한 TIL에 대한 즐겨찾기 설정을 변경하면 변경 내용 적용
     * @param userId 요청한 사용자 번호
     * @param request 요청 대상 TIL의 제목
     * @return TIL의 제목과 작성자 번호로 해당하는 TIL 객체를 찾아 북마크 상태 변경
     */
    @PatchMapping("/bookmark")
    public ResponseEntity<Void> changeBookMark(@PathVariable("user_id") Long userId,
                                               @RequestBody Map<String, String> request) {
        String tilTitle = request.get("tilTitle");
        Til til = tilService.changeBookMark(tilTitle, userId);

        return (til != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
