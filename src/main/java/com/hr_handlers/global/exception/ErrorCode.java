package com.hr_handlers.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //휴가
    VACATION_NOT_FOUND(HttpStatus.NOT_FOUND, "VACATION-01", "해당 휴가를 조회할 수 없습니다."),

    //근태

    //일정

    //게시판
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST-01", "해당 게시글을 찾을 수 없습니다."),
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "POST-02", "전체 게시글을 조회할 수 없습니다."),
    POST_CREATE_FAILED(HttpStatus.NOT_FOUND, "POST-03", "게시글 생성에 실패했습니다."),
    POST_UPDATE_FAILED(HttpStatus.NOT_FOUND, "POST-04", "게시글 수정에 실패했습니다."),
    POST_DELETE_FAILED(HttpStatus.NOT_FOUND, "POST-05", "게시글 삭제에 실패했습니다."),
    //채팅

    //급여

    //사원
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMPLOYEE-01", "사원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
