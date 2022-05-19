package click.bitbank.api.presentation.member.request;

import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.shared.request.RequestVerify;
import java.util.regex.Pattern;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupRequest implements RequestVerify {

    private String memberLoginId; // 회원 아이디
    private String memberName; // 회원 이름
    private String memberPassword; // 회원 비밀번호

    @Override
    public void verify() {
        verifyLoginId();
        verifyName();
        verifyPassword();
    }

    /**
     * 로그인 아이디 유효성 검사
     * 비어있는경우, 문자와 숫자로 구성되어있는지 확인, 8자 이상의 아이디만 가능하도록 검사
     */
    private void verifyLoginId() {
        if (StringUtils.isBlank(memberLoginId)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberLoginId.getMessage());
        if (!StringUtils.isAlphanumeric(memberLoginId)) throw new BadRequestException(ExceptionMessage.IsRequiredAlphaNumericForMemberLoginId.getMessage());
        if (StringUtils.length(memberLoginId) < 8) throw new BadRequestException(ExceptionMessage.IsRequiredStringLengthForMemberLoginId.getMessage());
    }

    /**
     * 회원 이름 유효성 검사
     * 비어있는경우, 문자로 구성되어 있는지 확인
     */
    private void verifyName() {
        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (!StringUtils.isAlpha(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredAlphaForMemberName.getMessage());
    }

    /**
     * 비밀번호 유효성 검사
     * 비어있는지, 영문/숫자/특수문자 포함 6자 이상의 비밀번호인지 검사
     */
    private void verifyPassword() {
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
        // 문자, 숫자, 특수문자 조합의 패턴인지 검사
        Pattern pattern = Pattern.compile("([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])");
        if (!pattern.matcher(memberPassword).find() || StringUtils.length(memberPassword) < 6) throw new BadRequestException(ExceptionMessage.IsRequiredCharNumberSpecialCharactersForMemberPassword.getMessage());
    }
}
