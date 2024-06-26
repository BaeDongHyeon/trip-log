package com.yeogi.triplog.domain.member.form;

import com.yeogi.triplog.domain.member.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignUpForm {

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    private String phone;

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickname(nickname)
                .phone(phone)
                .email(email)
                .password(password)
                .build();
    }
}
