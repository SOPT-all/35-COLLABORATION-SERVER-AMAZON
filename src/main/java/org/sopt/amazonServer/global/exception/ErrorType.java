package org.sopt.amazonServer.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    /* Common Error */
    INVALID_PATH_ERROR(HttpStatus.BAD_REQUEST, "40001", "요청 경로의 변수 값이 허용된 형식과 다릅니다."),
    INVALID_FIELD_ERROR(HttpStatus.BAD_REQUEST, "40002", "요청 본문의 필드 값이 허용된 형식과 다릅니다."),
    NO_REQUEST_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "40003", "요청에 필요한 파라미터가 존재하지 않습니다."),
    NO_REQUEST_HEADER_ERROR(HttpStatus.BAD_REQUEST, "40004", "요청에 필요한 헤더가 존재하지 않습니다."),
    TYPE_MISMATCH_ERROR(HttpStatus.BAD_REQUEST, "40005", "잘못된 값이 입력되었습니다."),
    INVALID_REQUEST_BODY_ERROR(HttpStatus.BAD_REQUEST, "40006", "잘못된 Request Body입니다. 요청 형식 또는 필드를 확인하세요."),
    DATA_INTEGRITY_VIOLATION_ERROR(HttpStatus.BAD_REQUEST, "40007", "데이터 무결성 제약 조건을 위반했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50001", "예상치 못한 서버 에러가 발생했습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "40401", "존재하지 않는 경로입니다."),

    /* Products Error */
    INVALID_PRODUCTS_SORT_BY_ERROR(HttpStatus.BAD_REQUEST, "40008", "잘못된 검색 결과 정렬 기준입니다."),
    PRODUCT_NOT_IN_CART_ERROR(HttpStatus.BAD_REQUEST, "40009", "장바구니에 존재하지 않는 상품입니다."),
    PRODUCT_IN_CART_ERROR(HttpStatus.BAD_REQUEST, "40901", "장바구니에 이미 존재하는 상품입니다."),

    /* Member Error */
    NOT_FOUND_MEMBER_ERROR(HttpStatus.NOT_FOUND, "40402", "존재하지 않는 회원입니다."),
    NOT_FOUND_PRODUCT_ERROR(HttpStatus.NOT_FOUND, "40403", "존재하지 않는 상품입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorType(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
