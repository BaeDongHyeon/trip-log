package com.yeogi.triplog.domain.member;

public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
