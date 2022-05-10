package click.bitbank.api.infrastructure.dao.member;

import click.bitbank.api.domain.model.member.Member;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import click.bitbank.api.domain.model.member.MemberFactory;
import click.bitbank.api.domain.model.member.MemberType;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.application.member.MemberSha256;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberFactoryImpl implements MemberFactory {

    /**
     * 회원 정보 세팅
     * @param memberName : 이름
     * @param memberPassword : 비밀번호
     * @param memberType : 회원 유형
     * @return Member : 회원 정보
     */
    @Override
    public Member memberBuilder(String memberName, String memberPassword, MemberType memberType) {

        if (StringUtils.isBlank(memberName)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberName.getMessage());
        if (StringUtils.isBlank(memberPassword)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberPassword.getMessage());
        if (memberType == null) throw new BadRequestException(ExceptionMessage.IsRequiredMemberType.getMessage());

        return Member.builder()
            .memberName(memberName)
            .memberPassword(MemberSha256.encrypt(memberPassword))
            .memberType(memberType)
            .build();
    }
}
