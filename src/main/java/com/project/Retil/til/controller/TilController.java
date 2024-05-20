package com.project.Retil.til.controller;

import com.project.Retil.til.service.TilService;
import com.project.Retil.til.service.TilServiceImpl;
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

    // 1. TIL 전체 리스트 조회
    @GetMapping("/")
    public List<Til> showList(@PathVariable Long user_id) {
        return tilService.showList(user_id);
    }

    // 2. TIL 과목별 리스트 조회

    // 3. TIL 에디터 보기
    @GetMapping("/{til_num}")
    public Til show(@PathVariable Long til_num) {
        return tilService.show(til_num);
    }

    // 4. TIL 작성 내용 임시 저장

    // 5. TIL 작성 완료 후 저장
    @PostMapping("/write")
    public ResponseEntity<Til> save(@RequestBody TilCreateDTO tilCreateDto,
                                    @PathVariable Long user_id) {
        Til created = tilService.save(tilCreateDto, user_id);
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
}
