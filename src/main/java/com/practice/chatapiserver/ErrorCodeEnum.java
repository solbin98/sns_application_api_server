package com.practice.chatapiserver;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    EXPIRED_TOKEN(400, "만료된 토큰입니다"),
    MEMBER_ALREADY_EXISTS(400, "이미 존재하는 회원 아이디 입니다"),
    PASSWORD_INCONSISTENCY(400, "비밀번호가 다릅니다"),
    INVALID_ARGUMENT(400, "잘못된 입력 입니다."),
    INVALID_TOKEN(400, "유효하지 않은 토큰입니다."),
    PARSING_ERROR(400, "토큰을 파싱하는데 실패했습니다"),
    REJECT_TOKEN(400, "잘못된 형식의 토큰입니다."),
    INVALID_PASSWORD(400, "비밀번호가 잘못되었습니다."),
    TOKEN_EMPTY(400, "토큰이 없습니다."),
    SERVER_INNER_ERROR(500, "서버 내부에서 문제가 발생했습니다."),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다.");

    int statusCode;
    String msg;

    ErrorCodeEnum(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
