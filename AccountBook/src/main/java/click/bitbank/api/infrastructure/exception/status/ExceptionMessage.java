package click.bitbank.api.infrastructure.exception.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    IsRequiredRequest("BadRequestException", "Request를 전달해주세요."),
    IsRequiredMemberId("BadRequestException", "전달된 회원 고유번호가 없습니다."),
    IsRequiredMemberName("BadRequestException", "이름을 입력해 주세요."),
    IsRequiredMemberPassword("BadRequestException", "비밀번호를 입력해 주세요."),
    IsRequiredMemberType("BadRequestException", "회원 유형을 선택해 주세요."),

    NotFoundLoginMember("UnauthorizedException", "회원정보를 확인해주세요."),

    AlreadyDataMember("AlreadyDataException", "이미 존재하는 회원입니다."),

    SaveFailMember("RegistrationFailException", "회원 가입에 실패했습니다. 관리자에게 문의 바랍니다."),

    IsRequiredSearchDateType("BadRequestException", "검색 기간 유형을 선택해 주세요."),

    IsRequiredAccountName("BadRequestException", "거래처명을 입력해주세요"),
    IsRequiredCreatedDate("BadRequestException", "작성한 날짜를 입력해주세요"),
    IsRequiredAccountType("BadRequestException", "가계부 내역 유형을 입력해주세요"),
    IsRequiredIncomeType("BadRequestException", "수입 카테고리를 입력해주세요"),
    IsRequiredExpenditureType("BadRequestException", "수입 카테고리를 입력해주세요"),
    IsRequiredTransferType("BadRequestException", "수입 카테고리를 입력해주세요"),
    IsRequiredPositiveNumber("BadRequest", "금액을 1원 이상 입력해주세요"),
    WriteFailAccountBook("WriteFailException", "가계부 작성 실패, 관리자에게 문의 바랍니다.");

    private final String type;
    private final String message;
}
