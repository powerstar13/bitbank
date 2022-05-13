package click.bitbank.api.domain.dao.factory;

import click.bitbank.api.domain.model.member.Member;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import click.bitbank.api.domain.model.member.MemberType;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.util.MemberSha256;

@Component
public class MemberFactoryImpl implements MemberFactory {

    /**
     * 회원 정보 세팅
     * @param memberLoginId : 아이디
     * @param memberName : 이름
     * @param memberPassword : 비밀번호
     * @return Member : 회원 정보
     */
    @Override
    public Member memberBuilder(String memberLoginId, String memberName, String memberPassword, MemberType memberType) {
        if (StringUtils.isBlank(memberLoginId)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberLoginId.getMessage());
        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
        if (memberType == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberType.getMessage());

        return Member.builder()
            .memberLoginId(memberLoginId)
            .memberName(memberName)
            .memberPassword(MemberSha256.encrypt(memberPassword))
            .memberType(memberType)
            .build();
    }
}
