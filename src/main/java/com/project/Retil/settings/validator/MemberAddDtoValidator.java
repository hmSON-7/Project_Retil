package com.project.Retil.settings.validator;

import com.project.Retil.userAccount.dto.JoinRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * UserAddDto 객체의 유효성 검사 클래스. Validator 인터페이스의 구현 클래스
 */
@Slf4j
@Component
public class MemberAddDtoValidator implements Validator {

    /**
     * 이 Validator가 주어진 클래스의 객체를 검증할 수 있는지 여부 반환
     * 해당 클래스에서는 UserAddDto와 그 하위 클래스만 검증
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return JoinRequestDTO.class.isAssignableFrom(clazz);
    }

    /**
     * 유효성 검사 수행 메서드
     * @param target : 검증할 대상 객체
     * @param errors : 검증 과정에서 발생한 오류를 담을 객체
     * password와 confirmPassword의 유효성 검사기 -> 일치 여부 검사
     */
    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        JoinRequestDTO joinDto = (JoinRequestDTO) target;

        if (!joinDto.getPassword().equals(joinDto.getConfirmPassword())) {
            errors.rejectValue("confirmPassword","passwordMismatch",
                    "확인 비밀 번호가 일치하지 않습니다.");
        }
    }
}