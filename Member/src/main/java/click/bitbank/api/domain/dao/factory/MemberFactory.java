package click.bitbank.api.domain.dao.factory;

import click.bitbank.api.domain.model.member.Member;
import click.bitbank.api.domain.model.member.MemberType;

public interface MemberFactory {

    Member memberBuilder(String memberLoginId, String memberName, String memberPassword, MemberType memberType); // 일반 회원 정보 세팅
    Member socialMemberBuilder(String socialToken, String memberName, MemberType memberType); // 소셜 회원 정보 세팅
}
