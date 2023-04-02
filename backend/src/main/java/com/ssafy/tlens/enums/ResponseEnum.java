package com.ssafy.tlens.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    TEST_SUCCESS(200,"테스트 유효성 검사 성공"),
    TEST_FAIL(500, "테스트 유효성 검사 실패"),

    FILE_UPLOAD_SUCCESS(200, "파일 업로드에 성공하였습니다"),
    FILE_UPLOAD_FAIL(500, "파일 업로드에 실패하였습니다"),

    FILE_NOT_FOUND(404, "파일을 찾을 수 없습니다"),

    AUTH_BAD_REQUEST(403, "bad request"),
    AUTH_INVALID_TOKEN(401, "invalid token"),
    AUTH_NOT_JOINED(405, "not joined user"),
    AUTH_REFRESH_DOES_NOT_EXIST(401, "REFRESH_DOES_NOT_EXIST"),
    AUTH_ACCESS_EXPIRED(499, "AUTH_ACCESS_EXPIRED"),
    AUTH_REFRESH_EXPIRED(444, "AUTH_REFRESH_EXPIRED"),
    AUTH_AUTHORITY_DENIED(445, "AUTH_AUTHORITY_DENIED"),

    USER_USERNAME_CK_SUCCESS(200, "사용가능한 아이디입니다."),
    USER_USERNAME_CK_FAIL(500, "사융할 수 없는 아이디입니다."),

    USER_JOIN_SUCCESS(200, "회원가입에 성공하였습니다."),
    USER_JOIN_FAIL(500, "다시 시도해주세요."),

    USER_MY_INFO_SUCCESS(200,"조회 성공"),
    USER_NOT_FOUND(401,"회원이 존재하지 않습니다"),
    USER_LOGOUT_SUCCESS(200,"로그아웃 성공"),
    USER_PROFILE_CHANGE_SUCCESS(200, "변경이 완료되었습니다"),
    USER_PROFILE_CHANGE_YET(200, "마지막 변경일로부터 30일 이후 변경 가능합니다"),

    USER_ADDRESS_ADD_SUCCESS(200, "동네추가에 성공하였습니다"),
    USER_ADDRESS_ADD_OVER_MAX(500, "최대 2개의 동네만 추가할 수 있습니다"),

    USER_MY_ADDRESSES_SUCCESS(200, "조회에 성공하였습니다"),

    USER_ADDRESS_NOT_FOUND(500, "존재하지 않는 동네입니다"),
    USER_ADDRESS_UPDATE_SUCCESS(200, "기본 동네 설정에 성공하였습니다"),

    USER_ADDRESS_REMOVE_SUCCESS(200, "동네삭제에 성공하였습니다"),
    USER_ADDRESS_REMOVE_MUST_HAVE_ONE(500, "하나의 동네는 필수입니다"),
    USER_ADDRESS_REMOVE_DO_NOT_HAVE_ADDRESS(500, "현재 등록되어 있지 않은 동네입니다"),

    PRODUCT_CATEGORY_SUCCESS(200,"조회에 성공하였습니다"),
    PRODUCT_CATEGORY_FAIL(400,"조회에 실패하였습니다"),

    PRODUCT_UPLOAD_SUCCESS(200,"상품 업로드에 성공하였습니다"),
    PRODUCT_UPLOAD_ADDRESS_NOT_MATCHED(404,"업로드할 수 없는 동네입니다"),

    PRODUCT_LIKE_SUCCESS(200,"성공"),
    PRODUCT_LIKE_FAIL(500,"실패"),

    PRODUCT_LIST_SUCCESS(200,"조회에 성공하였습니다"),
    PRODUCT_LIST_FAIL(400,"조회에 실패하였습니다"),

    PRODUCT_DETAIL_SUCCESS(200,"조회에 성공하였습니다"),
    PRODUCT_NOT_FOUND(400,"존재하지 않는 상품입니다"),

    PRODUCT_SEARCH_SUCCESS(200,"조회에 성공하였습니다"),
    PRODUCT_SEARCH_FAIL(400,"조회에 실패하였습니다"),

    PRODUCT_UPDATE_SUCCESS(200,"수정 성공"),
    PRODUCT_NO_PERMISSION(401,"권한없음"),

    PRODUCT_UPDATE_TIME_SUCCESS(200,"성공"),
    PRODUCT_UPDATE_TIME_FAIL(400,"횟수초과"),

    PRODUCT_DELETE_SUCCESS(200,"삭제 성공"),

    REDIS_USER_NOT_FOUND(401,"Redis에 해당 USER가 존재하지 않습니다."),
    RTK_NOT_MATCHED(401,"Redis에 저장된 해당 USER의 RTK와 요청 받은 RTK가 일치하지 않습니다."),
    ATK_REISSUE_SUCCESS(200,"ATK 재발급 성공"),
    TOKEN_TYPE_NOT_FOUND(401,"토큰의 타입이 존재하지 않습니다");


    private final int code;
    private final String message;

}