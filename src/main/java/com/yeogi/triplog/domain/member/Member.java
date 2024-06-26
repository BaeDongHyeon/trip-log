package com.yeogi.triplog.domain.member;

import com.yeogi.triplog.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String nickname;

    private String phone;

    private String email;

    private String password;

    private MemberRole memberRole;

    @Builder
    public Member(String name, String nickname, String phone, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public void updateRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public String getRole() {
        return memberRole.getRole();
    }
}
